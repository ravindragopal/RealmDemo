package com.example.chaitanya.realmdemo;

import android.content.Context;
import android.widget.Toast;

import com.example.chaitanya.realmdemo.Activity.AddDataActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 11/8/18,4:43 PM.
 * For : ISS 24/7, Pune.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddDataTest {

    private static final String NAME = "Ravindra";
    private static final String AGE = "26";
    private static final String MOBILE = "0123456789";
    private static final String EMAIL = "rrr@gmail.com";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    // you can mock concrete classes, not only interfaces
    AddDataActivity addDataActivity = mock(AddDataActivity.class);

    @Test
    public void addDataValidation() {

        assertTrue(addDataActivity.isValidEmail(EMAIL));
//        assertFalse(addDataActivity.isValidEmail(EMAIL));
        assertTrue(addDataActivity.isValidMobile(MOBILE));
//        assertFalse(addDataActivity.isValidMobile(MOBILE));
        assertTrue(addDataActivity.isNumeric(AGE));
//        assertFalse(addDataActivity.isNumeric(AGE));
        assertThat("Ravindra", is(NAME));

    }
}
