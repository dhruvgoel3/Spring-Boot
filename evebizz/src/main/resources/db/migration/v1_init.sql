-- ============================================================
-- EventHub Database Schema V1
-- ============================================================

-- ---- USERS & AUTH ----

CREATE TABLE users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    full_name   VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN','ORGANIZER','COMPANY','EXPERT') NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    avatar_url  VARCHAR(500),
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_users_email (email),
    INDEX idx_users_role (role)
);

CREATE TABLE refresh_tokens (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    token       VARCHAR(500) NOT NULL UNIQUE,
    user_id     BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    revoked     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_refresh_token (token)
);

CREATE TABLE email_verifications (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    token      VARCHAR(255) NOT NULL UNIQUE,
    user_id    BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used       BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE password_reset_tokens (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    token      VARCHAR(255) NOT NULL UNIQUE,
    user_id    BIGINT NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    used       BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ---- COMPANY PROFILES ----

CREATE TABLE company_profiles (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT NOT NULL UNIQUE,
    company_name     VARCHAR(255) NOT NULL,
    description      TEXT,
    website_url      VARCHAR(500),
    logo_url         VARCHAR(500),
    industry         VARCHAR(100),
    company_size     ENUM('STARTUP','SMALL','MEDIUM','LARGE','ENTERPRISE'),
    location         VARCHAR(255),
    linkedin_url     VARCHAR(500),
    is_verified      BOOLEAN NOT NULL DEFAULT FALSE,
    verified_at      TIMESTAMP,
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_company_industry (industry),
    INDEX idx_company_verified (is_verified)
);

-- ---- SPONSORSHIP LISTINGS ----

CREATE TABLE sponsorship_listings (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id          BIGINT NOT NULL,
    title               VARCHAR(255) NOT NULL,
    description         TEXT NOT NULL,
    budget_min          DECIMAL(12,2),
    budget_max          DECIMAL(12,2),
    currency            VARCHAR(10) NOT NULL DEFAULT 'INR',
    event_types         JSON,               -- ["HACKATHON","WORKSHOP","CONFERENCE"]
    industries_preferred JSON,              -- ["TECH","FINANCE"]
    location_preference VARCHAR(255),
    is_remote_allowed   BOOLEAN NOT NULL DEFAULT TRUE,
    what_we_offer       TEXT NOT NULL,      -- what company will provide
    what_we_expect      TEXT NOT NULL,      -- what company expects in return
    max_applications    INT,
    deadline            DATE,
    status              ENUM('ACTIVE','PAUSED','CLOSED','EXPIRED') NOT NULL DEFAULT 'ACTIVE',
    view_count          INT NOT NULL DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company_profiles(id) ON DELETE CASCADE,
    INDEX idx_listing_status (status),
    INDEX idx_listing_company (company_id),
    INDEX idx_listing_deadline (deadline)
);

-- ---- SPONSORSHIP APPLICATIONS ----

CREATE TABLE sponsorship_applications (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    listing_id      BIGINT NOT NULL,
    organizer_id    BIGINT NOT NULL,         -- user id of organizer
    event_name      VARCHAR(255) NOT NULL,
    event_date      DATE,
    event_description TEXT NOT NULL,
    expected_participants INT,
    proposal_text   TEXT NOT NULL,
    proposal_doc_url VARCHAR(500),
    status          ENUM('PENDING','UNDER_REVIEW','ACCEPTED','REJECTED','WITHDRAWN') NOT NULL DEFAULT 'PENDING',
    rejection_reason TEXT,
    reviewed_at     TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES sponsorship_listings(id) ON DELETE CASCADE,
    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY uq_application (listing_id, organizer_id),
    INDEX idx_app_status (status),
    INDEX idx_app_organizer (organizer_id),
    INDEX idx_app_listing (listing_id)
);

-- ---- CLUB PROFILES ----

CREATE TABLE club_profiles (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL UNIQUE,
    club_name       VARCHAR(255) NOT NULL,
    description     TEXT,
    university      VARCHAR(255) NOT NULL,
    city            VARCHAR(100),
    state           VARCHAR(100),
    logo_url        VARCHAR(500),
    website_url     VARCHAR(500),
    instagram_url   VARCHAR(500),
    linkedin_url    VARCHAR(500),
    member_count    INT,
    club_type       ENUM('TECHNICAL','CULTURAL','SOCIAL','SPORTS','ENTREPRENEURSHIP','OTHER'),
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_club_university (university),
    INDEX idx_club_city (city)
);

-- ---- COLLABORATION REQUESTS ----

CREATE TABLE collaboration_requests (
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    club_id             BIGINT NOT NULL,
    title               VARCHAR(255) NOT NULL,
    event_name          VARCHAR(255) NOT NULL,
    event_description   TEXT NOT NULL,
    event_date          DATE,
    event_type          ENUM('HACKATHON','WORKSHOP','SEMINAR','CONFERENCE','CULTURAL','SPORTS','OTHER') NOT NULL,
    what_we_offer       TEXT NOT NULL,
    what_we_need        TEXT NOT NULL,
    expected_participants INT,
    location            VARCHAR(255),
    is_remote           BOOLEAN NOT NULL DEFAULT FALSE,
    deadline            DATE,
    status              ENUM('OPEN','IN_PROGRESS','CONFIRMED','CLOSED','CANCELLED') NOT NULL DEFAULT 'OPEN',
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (club_id) REFERENCES club_profiles(id) ON DELETE CASCADE,
    INDEX idx_collab_status (status),
    INDEX idx_collab_club (club_id),
    INDEX idx_collab_event_type (event_type)
);

-- ---- COLLABORATION INTERESTS ----

CREATE TABLE collaboration_interests (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    request_id      BIGINT NOT NULL,
    interested_club_id BIGINT NOT NULL,
    message         TEXT,
    status          ENUM('PENDING','ACCEPTED','REJECTED','WITHDRAWN') NOT NULL DEFAULT 'PENDING',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (request_id) REFERENCES collaboration_requests(id) ON DELETE CASCADE,
    FOREIGN KEY (interested_club_id) REFERENCES club_profiles(id) ON DELETE CASCADE,
    UNIQUE KEY uq_collab_interest (request_id, interested_club_id),
    INDEX idx_interest_status (status)
);

-- ---- EXPERT PROFILES ----

CREATE TABLE expert_profiles (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL UNIQUE,
    bio             TEXT NOT NULL,
    headline        VARCHAR(255),
    company         VARCHAR(255),
    designation     VARCHAR(255),
    location        VARCHAR(255),
    profile_pic_url VARCHAR(500),
    linkedin_url    VARCHAR(500),
    twitter_url     VARCHAR(500),
    website_url     VARCHAR(500),
    years_experience INT,
    is_available    BOOLEAN NOT NULL DEFAULT TRUE,
    roles_offered   JSON,   -- ["SPEAKER","JUDGE","MENTOR"]
    topics          JSON,   -- ["AI/ML","CLOUD","WEB3"]
    event_types     JSON,   -- ["HACKATHON","CONFERENCE"]
    fee_type        ENUM('FREE','PAID','NEGOTIABLE') DEFAULT 'NEGOTIABLE',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_expert_available (is_available),
    FULLTEXT INDEX ft_expert_bio (bio, headline)
);

CREATE TABLE expert_tags (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    expert_id  BIGINT NOT NULL,
    tag        VARCHAR(100) NOT NULL,
    FOREIGN KEY (expert_id) REFERENCES expert_profiles(id) ON DELETE CASCADE,
    INDEX idx_expert_tags (expert_id, tag),
    INDEX idx_tag (tag)
);

-- ---- EXPERT INVITATIONS ----

CREATE TABLE expert_invitations (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    expert_id       BIGINT NOT NULL,
    organizer_id    BIGINT NOT NULL,
    event_name      VARCHAR(255) NOT NULL,
    event_date      DATE,
    event_location  VARCHAR(255),
    role_requested  ENUM('SPEAKER','JUDGE','MENTOR') NOT NULL,
    message         TEXT NOT NULL,
    status          ENUM('PENDING','ACCEPTED','DECLINED','WITHDRAWN') NOT NULL DEFAULT 'PENDING',
    response_message TEXT,
    responded_at    TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (expert_id) REFERENCES expert_profiles(id) ON DELETE CASCADE,
    FOREIGN KEY (organizer_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_invitation_expert (expert_id),
    INDEX idx_invitation_organizer (organizer_id),
    INDEX idx_invitation_status (status)
);

-- ---- NOTIFICATIONS ----

CREATE TABLE notifications (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    type            VARCHAR(100) NOT NULL,
    title           VARCHAR(255) NOT NULL,
    message         TEXT NOT NULL,
    is_read         BOOLEAN NOT NULL DEFAULT FALSE,
    action_url      VARCHAR(500),
    metadata        JSON,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_notification_user (user_id, is_read),
    INDEX idx_notification_created (created_at)
);

-- ---- ADMIN / REPORTS ----

CREATE TABLE reports (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id     BIGINT NOT NULL,
    entity_type     ENUM('SPONSORSHIP_LISTING','COLLABORATION_REQUEST','EXPERT_PROFILE','COMPANY','CLUB') NOT NULL,
    entity_id       BIGINT NOT NULL,
    reason          VARCHAR(255) NOT NULL,
    details         TEXT,
    status          ENUM('PENDING','REVIEWED','RESOLVED','DISMISSED') NOT NULL DEFAULT 'PENDING',
    reviewed_by     BIGINT,
    reviewed_at     TIMESTAMP,
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reporter_id) REFERENCES users(id),
    INDEX idx_report_status (status)
);