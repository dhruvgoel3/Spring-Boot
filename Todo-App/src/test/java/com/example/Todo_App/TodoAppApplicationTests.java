package com.example.Todo_App;

import com.example.Todo_App.services.TodoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoAppApplicationTests {

	@Test
    public void sunofTwoNumber()
    {
        int result = TodoService.addTwoNumbers(12,45);
        int expected = 50;
        Assert.assertEquals(expected,result);

    }

}
