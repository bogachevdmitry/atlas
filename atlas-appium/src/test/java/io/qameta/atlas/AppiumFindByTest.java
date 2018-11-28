package io.qameta.atlas;

import io.qameta.atlas.annotations.AndroidFindBy;
import io.qameta.atlas.extension.AppiumFindByExtension;
import io.qameta.atlas.extension.Param;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static io.qameta.atlas.testdata.ObjectFactory.mockAndroidDriver;
import static io.qameta.atlas.testdata.ObjectFactory.mockWebElement;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AppiumFindByTest {

    private WebElement parent = mockWebElement();
    private Atlas atlas;

    @Test
    public void shouldParameterizedFindBy() {
        when(parent.findElement(By.xpath(anyString()))).thenReturn(mockWebElement());

        final Atlas atlas = new Atlas(new AppiumDriverConfiguration(mockAndroidDriver()));
        atlas.extension(new AppiumFindByExtension());

        String param = randomAlphanumeric(10);

        ParentElement atlasMobileElement = atlas.create(parent, ParentElement.class);
        atlasMobileElement.childElement(param).isDisplayed();

        verify(parent, times(1)).findElement(By.xpath(String.format("//div[%s]", param)));
    }

    private interface ParentElement extends AtlasWebElement {
        @AndroidFindBy(xpath = "//div[{{ value }}]")
        AtlasWebElement childElement(@Param("value") String value);
    }



}