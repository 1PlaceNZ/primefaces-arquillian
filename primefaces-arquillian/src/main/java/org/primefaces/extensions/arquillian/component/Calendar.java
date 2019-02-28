/**
 * Copyright 2011-2018 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.arquillian.component;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindById;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class Calendar extends AbstractInputComponent {

    @FindBy(className = "ui-datepicker-trigger")
    private WebElement popupButton;

    @FindById(value = "ui-datepicker-div")
    private WebElement datePicker;

    @FindByParentPartialId(value = "_input")
    private WebElement inputBox;

    protected WebElement getInput() {
        return inputBox;
    }

    protected boolean isOnchangeAjaxified() {
        return PrimeGraphene.isAjaxScript(getInput().getAttribute("onchange"));
    }

    @Override
    public WebElement getInput() {
        return root.findElement(By.id(getId() + "_input"));
    }

    public String getValue() {
        return getInput().getAttribute("value");
    }

    public void setValue(Serializable value) {
        getInput().clear();
        getInput().sendKeys(value.toString());

        if (isOnchangeAjaxified()) {
            try {
                Graphene.guardAjax(getInput()).sendKeys(Keys.TAB);
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            getInput().sendKeys(Keys.TAB);
        }
    }

    public void clearSelectedDate() {
        getInput().clear();

        if (isOnchangeAjaxified()) {
            try {
                Graphene.guardAjax(getInput()).sendKeys(Keys.TAB);
            }
            catch (RequestGuardException e) {
                PrimeGraphene.handleRequestGuardException(e);
            }
        }
        else {
            getInput().sendKeys(Keys.TAB);
        }
    }

    public void type(Date date, String dateFormatString) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        String formattedDate = dateFormat.format(date);
        setValue(formattedDate);
    }

    public void clickDay( int day ) {
        openCalendar();
        clickDayOfMonth(day);
    }

    private void clickDayOfMonth(int wishedDay) {
        List<WebElement> days = datePicker.findElements(By
                .tagName("td"));
        String txt;
        for (WebElement i : days) {
            txt = i.getText().trim();
            if (!txt.isEmpty()) {
                int day = new Integer(txt);
                if (day == wishedDay) {
                    i.click();
                    break;
                }
            }
        }
    }

    public void clickEnterDate( Date  date ) {
        java.util.Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int wishedYear = cal.get(java.util.Calendar.YEAR);
        // month is indexed from 0!
        int wishedMonth = cal.get(java.util.Calendar.MONTH);
        int wishedDay = cal.get(java.util.Calendar.DAY_OF_MONTH);
        openCalendar();
        WebElement currrentYear = datePicker.findElement(By.className("ui-datepicker-year"));
        WebElement currrentMonth = datePicker.findElement(By.className("ui-datepicker-month"));

        int todayYear = new Integer(currrentYear.getText());
        //English months
        int todayMonth = Month.valueOf(currrentMonth.getText().toUpperCase()).getValue();

        LocalDate nowDateTime = LocalDate.of(todayYear, todayMonth, 1);
        LocalDate withDate = LocalDate.of(wishedYear, wishedMonth + 1, 1);
        long months = ChronoUnit.MONTHS.between(nowDateTime, withDate);

        if (months > 0) {
            WebElement nextMonth = datePicker.findElement(By.className("ui-datepicker-next"));
            for (int i = 0; i < months; i++) {
                nextMonth.click();
            }
        }
        else if (months < 0) {
            WebElement nextMonth = datePicker.findElement(By.className("ui-datepicker-prev"));
            long preMonths = -months;
            for (int i = 0; i < preMonths; i++) {
                nextMonth.click();
            }
        }
        clickDayOfMonth(wishedDay);
        PrimeGraphene.waitGui().until().element(datePicker).is().not().visible();
    }

    public void setDate(Date date, String dateFormatString) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        String formattedDate = dateFormat.format(date);
        PrimeGraphene.executeScript(getWidgetByIdScript() + ".setDate(" + formattedDate + ");");
    }

    private void openCalendar() {
        if (popupButton.isDisplayed()) {
            if (!datePicker.isDisplayed()) {
                popupButton.click();
                PrimeGraphene.waitGui().until().element(datePicker).is().visible();
            }
        }
    }

    private void closeCalendar() {
        if (popupButton.isDisplayed()) {
            if (datePicker.isDisplayed()) {
                popupButton.click();
                PrimeGraphene.waitGui().until().element(datePicker).is().not().visible();
            }
        }
    }
}
