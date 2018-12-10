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
package org.primefaces.extensions.arquillian.example;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Default;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.enricher.PageObjectEnricher;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.embedded.EmbeddedMaven;
import org.junit.Assert;
import org.junit.Test;
import org.primefaces.PrimeFaces;
import org.primefaces.extensions.arquillian.AbstractPrimePageTest;

public class ComponentsPageTest extends AbstractPrimePageTest {
    
    @Deployment(testable = false)
    public static WebArchive createDeployment()
    {
        WebArchive webArchive = (WebArchive) EmbeddedMaven.forProject(new File("pom.xml"))
                .useMaven3Version("3.3.9")
                .setGoals("package")
                .setQuiet()
                .skipTests(true)
                .ignoreFailure()
                .build().getDefaultBuiltArchive();

        return webArchive;
    }
    
    @Test
    public void myComponentsTest(@InitialPage ComponentsPage components) throws Exception {
        // right page?
        Assert.assertTrue(components.isAt());
        assertNotDisplayed(components.getPassword().getPanel());
        
        components.getPassword().setValueAndNotNoTabOut("password");
        assertTrue(components.getPassword().isFeedBackDisplayed());
        assertEquals("Weak",components.getPassword().getFeedBackLabel());
        // just to follow the browser with a human eye for the showcase :D - not need in your real tests
        Thread.sleep(2000);
        assertEquals("false",components.getSelectBooleanCheckboxResult().getText());
        assertFalse(components.getSelectBooleanCheckbox().isSelected());
        components.getSelectBooleanCheckbox().select();
        assertTrue(components.getSelectBooleanCheckbox().isSelected());
        
        
        String bmw = "BMW";
        String vw = "VW";
        String mercedes = "Mercedes";
        
        
        assertFalse(components.getSelectManyMenu().isSelected(bmw));
        assertFalse(components.getSelectManyMenu().isSelected(mercedes));
        components.getSelectManyMenu().select(bmw);
        components.getSelectManyMenu().altSelect(mercedes);
        assertTrue(components.getSelectManyMenu().isSelected(bmw));
        assertTrue(components.getSelectManyMenu().isSelected(mercedes));
        
        String porsche = "Porsche";
        String ferrari = "Ferrari";
        String astonMartin = "Aston Martin";
        
        assertFalse(components.getSelectManyCheckbox().isSelected(porsche));
        assertFalse(components.getSelectManyCheckbox().isSelected(astonMartin));
        components.getSelectManyCheckbox().select(porsche);
        components.getSelectManyCheckbox().select(astonMartin);
        assertTrue(components.getSelectManyCheckbox().isSelected(porsche));
        assertTrue(components.getSelectManyCheckbox().isSelected(astonMartin));

        String ford ="Ford";
        String mazda = "Mazda";
        String toyato = "Toyato";
        String honda = "Honda";
        
        assertEquals(0,components.getPickList().getTargetSize());
        assertEquals(4,components.getPickList().getSourceSize());
        components.getPickList().selectFromSourceAndAdd(ford);
        components.getPickList().selectFromSourceAndDoubleClick(mazda);
        components.getPickList().clickSelectAll();
        assertEquals(4,components.getPickList().getTargetSize());
        assertEquals(0,components.getPickList().getSourceSize());
        
        components.getPickList().selectFromTargetAndRemove(ford);
        components.getPickList().selectFromTargetAndRemove(mazda);
        components.getPickList().clickRemoveAll();
        
        assertEquals(0,components.getPickList().getTargetSize());
        assertEquals(4,components.getPickList().getSourceSize());
        
        components.getPickList().selectFromSourceAndAdd(ford);
        components.getPickList().selectFromSourceAndDoubleClick(honda);
        
        Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);
		cal.set(MONTH, 6);//july
		cal.set(YEAR, 2018);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date calendarDate =cal.getTime();
        components.getCalendar().clickEnterDate(calendarDate);
        
        File file = new File(this.getClass().getResource("/testfiles/test.txt").getFile());
        components.getFileUpload().selectFile(file.getAbsolutePath());
        
        components.getInplaceWrapper().doubleClick();
        components.getInplace().isDisplayed();
        Graphene.waitGui().until().element(components.getInplace()).is().enabled();
        String inplaceMessage = "inplace text";
        components.getInplace().setValue(inplaceMessage);
        components.getInplaceWrapper().clickSave();
        
        String autocomplete ="Car 3";
        components.getAutocomplete().typeAutocompleteAndSelect(autocomplete, 3);
        components.getSignature().drawSignature();
        components.getButton().click();
        components.getDialog().waitUntilDisplayed();
        components.getDialog().close();
        components.getDialog().waitUntilNotDisplayed();
        assertEquals("Calendar fileupload",components.getTabView().getCurrentTabLabel());
        assertEquals("",components.getDate().getText());
        assertEquals("",components.getFileName().getText());
        
        components.getTabView().clickTab("inline, autocomplete"); 
        assertTrue(components.getTabView().getTab("tab2").isDisplayed());
        assertEquals("",components.getInline().getText());
        assertEquals("",components.getAutocompleteResult().getText());
       
        components.getTabView().clickTab("Signature"); 
        assertTrue(components.getTabView().getTab("tab3").isDisplayed());
       
        components.getSubmit().click();
        components = PageObjectEnricher.setupPage(GrapheneContext.getContextFor(Default.class), webDriver, ComponentsPage.class);
        
        assertEquals("true",components.getSelectBooleanCheckboxResult().getText());
        assertEquals(2,components.getSelectManyMenuDatatable().getRowCount());
        assertEquals("1",components.getSelectManyMenuDatatable().getCellText(0, 0));
        assertEquals(bmw,components.getSelectManyMenuDatatable().getCellText(0, 1));
       
        assertEquals("3",components.getSelectManyMenuDatatable().getCellText(1, "id"));
        assertTrue(components.getSelectManyMenuDatatable().getWebElementInCell(1, "link").isDisplayed());
        
        assertEquals(2,components.getSelectManyCheckboxDatalist().getListCount());
        assertTrue(components.getSelectManyCheckboxDatalist().isInList("4, Porsche"));
        assertTrue(components.getSelectManyCheckboxDatalist().isInList("6, Aston Martin"));
        
        assertEquals(2,components.getPickListDatalist().getListCount());
        assertTrue(components.getPickListDatalist().isInList("7, Ford"));
        assertTrue(components.getPickListDatalist().isInList("10, Honda"));
      
        components.getTabView().clickTab("Calendar fileupload"); 
        assertEquals("Calendar fileupload",components.getTabView().getCurrentTabLabel());
        assertEquals(calendarDate.toString(),components.getDate().getText());
        assertEquals("test.txt",components.getFileName().getText());
        
        components.getTabView().clickTab("inline, autocomplete"); 
        assertTrue(components.getTabView().getTab("tab2").isDisplayed());
        assertEquals(inplaceMessage,components.getInline().getText());
        assertEquals(autocomplete,components.getAutocompleteResult().getText());
       
        components.getTabView().clickTab("Signature"); 
        assertTrue(components.getTabView().getTab("tab3").isDisplayed());
        assertTrue(components.getSignatureResult().isPresent());
        
        assertTrue(!components.getSignatureResult().getValue().equals(""));
        
        
        
        
    }
}
