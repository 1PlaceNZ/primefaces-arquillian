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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.PrimeExpectedConditions;
import org.primefaces.extensions.arquillian.PrimeGraphene;
import org.primefaces.extensions.arquillian.component.base.AbstractInputComponent;
import org.primefaces.extensions.arquillian.extension.findby.FindByParentPartialId;

public abstract class SelectCheckboxMenu extends AbstractInputComponent {
	
	@FindBy(className = "ui-selectcheckboxmenu-trigger")
	private WebElement panelTrigger;

    @FindBy(className = "ui-selectcheckboxmenu-token-label")
    private List<WebElement> selectedItems;
    
    @FindBy(className = "ui-selectcheckboxmenu-token-icon")
    private List<WebElement> selectedItemsRemoveIcon;

    @FindBy(tagName = "input")
    private List<WebElement> inputs;

    @FindByParentPartialId(value = "_panel", searchFromRoot = true)
    private WebElement panel;
    
    
    public List<String> getOptionLabels() {
        List<String> result = new ArrayList<>();
        List<WebElement> options = panel.findElements(By.tagName("li"));
        for (WebElement option : options) {
        	WebElement labelElement = option.findElement(By.tagName("label"));
            result.add(labelElement.getAttribute("textContent")); //getText can only be used on visible elements
        }
        return result;
    }
    
    public boolean containOption(String optionLabel) {
        List<WebElement> options = panel.findElements(By.tagName("li"));
        for (WebElement option : options) {
        	String label = option.findElement(By.tagName("label")).getAttribute("textContent"); //getText can only be used on visible elements
            if (optionLabel.equals(label)) {
                return true;
            }
        }
        return false;
    }
    
    public void toggleDropdown() {
        if (panel.isDisplayed()) {
        	panelTrigger.click();
            PrimeGraphene.waitGui().until(PrimeExpectedConditions.invisibleAndAnimationComplete(panel));
        }
        else {
        	panelTrigger.click();
            PrimeGraphene.waitGui().until(PrimeExpectedConditions.visibileAndAnimationComplete(panel));
        }
    }
    
    public void select(String label) {
        if (isSelected(label)) {
            return;
        }
        unselectAll();
        showPanel();
        toggleSelection(label);
        hidePanel();
    }
    
    public void unselect(String label) {
    	if (!isSelected(label)) {
            return;
        }
    	showPanel();
        toggleSelection(label);
        hidePanel();
    }
    
    public void selectMultiple(String label) {
        if (isSelected(label)) {
            return;
        }
        showPanel();
        toggleSelection(label);
        hidePanel();
    }
    
    public void unselectAll() {
    	if(selectedItemsRemoveIcon != null) {
    		selectedItemsRemoveIcon.forEach( item -> item.click());
    	}
    }
    
    // to keep compatibility with selectonemenu
    public String getSelectedLabel() {
        return selectedItems!= null && 
        		!selectedItems.isEmpty() ? selectedItems.get(0).getText() : "";
    }
    
    public List<String> getSelectedLabels() {
    	List<String> texts = new ArrayList<String>();
        if (selectedItems!= null && !selectedItems.isEmpty()) {
        	texts = selectedItems.stream().map(label -> label.getText()).collect(Collectors.toList());
        }
        return texts; 
    }

    public boolean isSelected(String label) {
        return getSelectedLabels().contains(label);
    }

    @Override
    protected WebElement getInput() {
        return inputs != null && !inputs.isEmpty() ? inputs.get(0) : null;
    }

    protected void click(WebElement element) {
    	//cannot click on input -> "element not interactable" exception
    	element.findElement(By.className("ui-chkbox")).click();
    }
    
    private void showPanel() {
    	if (!panel.isDisplayed()) {
            toggleDropdown();
        }
    }
    
    private void hidePanel() {
    	if (panel.isDisplayed()) {
            toggleDropdown();
        }
    }
    
    private void toggleSelection(String label) {
    	panel.findElements(By.tagName("li")).forEach(element -> {
        	String labelItem = element.findElement(By.tagName("label")).getText();
            if (labelItem.equalsIgnoreCase(label)) {
                click(element);
            }
        });
    }
    
}
