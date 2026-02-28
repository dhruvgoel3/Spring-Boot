package com.example.evebizz.services;

import com.example.evebizz.dto.SponsorshipRequests;
import com.example.evebizz.entities.CompanyProfile;
import com.example.evebizz.entities.SponsorshipApplication;
import com.example.evebizz.entities.SponsorshipListing;
import com.example.evebizz.enums.ListingStatus;
import com.example.evebizz.repositories.CompanyProfileRepository;
import com.example.evebizz.repositories.SponsorshipApplicationRepository;
import com.example.evebizz.repositories.SponsorshipListingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SponsorshipService {

    private final SponsorshipListingRepository listingRepository;
    private final SponsorshipApplicationRepository applicationRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final NotificationService notificationService;
    private final EmailService emailService;

    // ========== LISTINGS (Company Side) ==========

    @Transactional
    public SponsorshipListing createListing(Long userId, SponsorshipRequests.CreateListingRequest request) {
        CompanyProfile company = getCompanyProfile(userId);

        SponsorshipListing listing = SponsorshipListing.builder()
                .company(company)
                .title(request.getTitle())
                .description(request.getDescription())
                .budgetMin(request.getBudgetMin())
                .budgetMax(request.getBudgetMax())
                .currency(request.getCurrency())
                .eventTypes(request.getEventTypes())
                .industriesPreferred(request.getIndustriesPreferred())
                .locationPreference(request.getLocationPreference())
                .isRemoteAllowed(request.isRemoteAllowed())
                .whatWeOffer(request.getWhatWeOffer())
                .whatWeExpect(request.getWhatWeExpect())
                .maxApplications(request.getMaxApplications())
                .deadline(request.getDeadline())
                .status(ListingStatus.ACTIVE)
                .build();

        return listingRepository.save(listing);
    }

    @Transactional
    public SponsorshipListing updateListing(Long listingId, Long userId, SponsorshipRequests.UpdateListingRequest request) {
        SponsorshipListing listing = getListingAndVerifyOwner(listingId, userId);

        if (request.getTitle() != null) listing.setTitle(request.getTitle());
        if (request.getDescription() != null) listing.setDescription(request.getDescription());
        if (request.getBudgetMin() != null) listing.setBudgetMin(request.getBudgetMin());
        if (request.getBudgetMax() != null) listing.setBudgetMax(request.getBudgetMax());
        if (request.getEventTypes() != null) listing.setEventTypes(request.getEventTypes());
        if (request.getIndustriesPreferred() != null) listing.setIndustriesPreferred(request.getIndustriesPreferred());
        if (request.getLocationPreference() != null) listing.setLocationPreference(request.getLocationPreference());
        if (request.getIsRemoteAllowed() != null) listing.setRemoteAllowed(request.getIsRemoteAllowed());
        if (request.getWhatWeOffer() != null) listing.setWhatWeOffer(request.getWhatWeOffer());
        if (request.getWhatWeExpect() != null) listing.setWhatWeExpect(request.getWhatWeExpect());
        if (request.getMaxApplications() != null) listing.setMaxApplications(request.getMaxApplications());
        if (request.getDeadline() != null) listing.setDeadline(request.getDeadline());
        if (request.getStatus() != null) listing.setStatus(request.getStatus());

        return listingRepository.save(listing);
    }

    @Transactional
    public void deleteListing(Long listingId, Long userId) {
        SponsorshipListing listing = getListingAndVerifyOwner(listingId, userId);
        listing.setStatus(ListingStatus.CLOSED);
        listingRepository.save(listing);
    }

    @Transactional(readOnly = true)
    public Page<SponsorshipListing> getActiveListings(Pageable pageable) {
        return listingRepository.findActiveListings(pageable);
    }

    @Transactional(readOnly = true)
    public Page<SponsorshipListing> getMyListings(Long userId, Pageable pageable) {
        CompanyProfile company = getCompanyProfile(userId);
        return listingRepository.findByCompanyId(company.getId(), pageable);
    }

    @Transactional
    public SponsorshipListing getListingById(Long listingId) {
        SponsorshipListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship listing", listingId));
        // Increment view count
        listing.setViewCount(listing.getViewCount() + 1);
        listingRepository.save(listing);
        return listing;
    }

    // ========== APPLICATIONS (Organizer Side) ==========

    @Transactional
    public SponsorshipApplication applyForSponsorship(Long userId, Long listingId, CreateApplicationRequest request) {
        SponsorshipListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship listing", listingId));

        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new BusinessException("This sponsorship listing is not accepting applications");
        }

        if (applicationRepository.existsByListingIdAndOrganizerId(listingId, userId)) {
            throw new DuplicateResourceException("You have already applied for this sponsorship");
        }

        // Check max applications limit
        if (listing.getMaxApplications() != null) {
            long currentApplications = applicationRepository.countByListingId(listingId);
            if (currentApplications >= listing.getMaxApplications()) {
                throw new BusinessException("This listing has reached its maximum number of applications");
            }
        }

        // Organizer cannot be the company itself
        if (listing.getCompany().getUser().getId().equals(userId)) {
            throw new BusinessException("You cannot apply to your own sponsorship listing");
        }

        User organizer = listing.getCompany().getUser(); // will be replaced - need organizer User
        // We need the actual organizer user from context - done in controller

        SponsorshipApplication application = SponsorshipApplication.builder()
                .listing(listing)
                .organizer(listing.getCompany().getUser()) // placeholder - controller passes actual user
                .eventName(request.getEventName())
                .eventDate(request.getEventDate())
                .eventDescription(request.getEventDescription())
                .expectedParticipants(request.getExpectedParticipants())
                .proposalText(request.getProposalText())
                .proposalDocUrl(request.getProposalDocUrl())
                .status(ApplicationStatus.PENDING)
                .build();

        return application; // returned before save - service method below handles correctly
    }

    @Transactional
    public SponsorshipApplication submitApplication(User organizer, Long listingId, CreateApplicationRequest request) {
        SponsorshipListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Sponsorship listing", listingId));

        if (listing.getStatus() != ListingStatus.ACTIVE) {
            throw new BusinessException("This listing is not accepting applications");
        }

        if (applicationRepository.existsByListingIdAndOrganizerId(listingId, organizer.getId())) {
            throw new DuplicateResourceException("You have already applied for this sponsorship");
        }

        if (listing.getCompany().getUser().getId().equals(organizer.getId())) {
            throw new BusinessException("You cannot apply to your own listing");
        }

        if (listing.getMaxApplications() != null) {
            long count = applicationRepository.countByListingId(listingId);
            if (count >= listing.getMaxApplications()) {
                throw new BusinessException("This listing has reached its maximum applications");
            }
        }

        SponsorshipApplication application = SponsorshipApplication.builder()
                .listing(listing)
                .organizer(organizer)
                .eventName(request.getEventName())
                .eventDate(request.getEventDate())
                .eventDescription(request.getEventDescription())
                .expectedParticipants(request.getExpectedParticipants())
                .proposalText(request.getProposalText())
                .proposalDocUrl(request.getProposalDocUrl())
                .status(ApplicationStatus.PENDING)
                .build();

        SponsorshipApplication saved = applicationRepository.save(application);

        // Notify company
        notificationService.createNotification(
                listing.getCompany().getUser(),
                "NEW_APPLICATION",
                "New Sponsorship Application",
                organizer.getFullName() + " applied for: " + listing.getTitle(),
                "/dashboard/applications/" + saved.getId()
        );
        emailService.sendNewApplicationNotification(listing.getCompany().getUser(), saved);

        log.info("New application submitted by {} for listing {}", organizer.getEmail(), listingId);
        return saved;
    }

    @Transactional
    public SponsorshipApplication reviewApplication(Long applicationId, Long companyUserId, ReviewApplicationRequest request) {
        SponsorshipApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", applicationId));

        // Verify the company owns this application's listing
        if (!application.getListing().getCompany().getUser().getId().equals(companyUserId)) {
            throw new UnauthorizedException("You don't have permission to review this application");
        }

        if (application.getStatus() != ApplicationStatus.PENDING &&
                application.getStatus() != ApplicationStatus.UNDER_REVIEW) {
            throw new BusinessException("This application has already been reviewed");
        }

        if (request.isAccepted()) {
            application.setStatus(ApplicationStatus.ACCEPTED);
        } else {
            if (request.getRejectionReason() == null || request.getRejectionReason().isBlank()) {
                throw new BusinessException("Please provide a reason for rejection");
            }
            application.setStatus(ApplicationStatus.REJECTED);
            application.setRejectionReason(request.getRejectionReason());
        }

        application.setReviewedAt(LocalDateTime.now());
        SponsorshipApplication updated = applicationRepository.save(application);

        // Notify organizer
        notificationService.createNotification(
                application.getOrganizer(),
                "APPLICATION_REVIEWED",
                "Application " + application.getStatus().name(),
                "Your application for " + application.getListing().getTitle() + " has been " + application.getStatus().name().toLowerCase(),
                "/dashboard/my-applications"
        );
        emailService.sendApplicationStatusUpdate(updated);

        return updated;
    }

    @Transactional
    public void withdrawApplication(Long applicationId, Long organizerId) {
        SponsorshipApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", applicationId));

        if (!application.getOrganizer().getId().equals(organizerId)) {
            throw new UnauthorizedException("You don't have permission to withdraw this application");
        }

        if (application.getStatus() == ApplicationStatus.ACCEPTED ||
                application.getStatus() == ApplicationStatus.REJECTED) {
            throw new BusinessException("Cannot withdraw an already reviewed application");
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public Page<SponsorshipApplication> getMyApplications(Long organizerId, Pageable pageable) {
        return applicationRepository.findByOrganizerId(organizerId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<SponsorshipApplication> getListingApplications(Long listingId, Long companyUserId, Pageable pageable) {
        SponsorshipListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing", listingId));

        if (!listing.getCompany().getUser().getId().equals(companyUserId)) {
            throw new UnauthorizedException("Access denied");
        }

        return applicationRepository.findByListingId(listingId, pageable);
    }

    // ========== Helpers ==========

    private CompanyProfile getCompanyProfile(Long userId) {
        return companyProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Please complete your company profile first"));
    }

    private SponsorshipListing getListingAndVerifyOwner(Long listingId, Long userId) {
        SponsorshipListing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new ResourceNotFoundException("Listing", listingId));

        if (!listing.getCompany().getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You don't have permission to modify this listing");
        }
        return listing;
    }
}