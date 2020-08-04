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

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.arquillian.AbstractPrimePage;
import org.primefaces.extensions.arquillian.component.AutoComplete;
import org.primefaces.extensions.arquillian.component.Calendar;
import org.primefaces.extensions.arquillian.component.CommandButton;
import org.primefaces.extensions.arquillian.component.DataList;
import org.primefaces.extensions.arquillian.component.DataTable;
import org.primefaces.extensions.arquillian.component.DatePicker;
import org.primefaces.extensions.arquillian.component.Dialog;
import org.primefaces.extensions.arquillian.component.FileUpload;
import org.primefaces.extensions.arquillian.component.Inplace;
import org.primefaces.extensions.arquillian.component.InputText;
import org.primefaces.extensions.arquillian.component.Password;
import org.primefaces.extensions.arquillian.component.PickList;
import org.primefaces.extensions.arquillian.component.SelectBooleanCheckbox;
import org.primefaces.extensions.arquillian.component.SelectManyCheckbox;
import org.primefaces.extensions.arquillian.component.SelectManyMenu;
import org.primefaces.extensions.arquillian.component.Signature;
import org.primefaces.extensions.arquillian.component.TabView;

@Location("components.xhtml")
public class ComponentsPage extends AbstractPrimePage {
    
    @FindBy(id = "form:password")
    private Password password;
    
    @FindBy(id = "form:selectbooleancheckbox")
    private SelectBooleanCheckbox selectBooleanCheckbox;

    @FindBy(id = "form:selectManyMenu")
    private SelectManyMenu selectManyMenu;
    
    @FindBy(id = "form:selectManyCheckbox")
    private SelectManyCheckbox selectManyCheckbox;
    
    @FindBy(id = "form:pickList")
    private PickList pickList;
    
    @FindBy(id = "form:calendar")
    private Calendar calendar;

    
    @FindBy(id = "form:datepicker")
    private DatePicker datePicker;
    
    @FindBy(id = "form:fileupload")
    private FileUpload fileUpload;
    
    @FindBy(id = "form:inplacewrapper")
    private Inplace inplaceWrapper;
    
    @FindBy(id = "form:inplace")
    private InputText inplace;

    @FindBy(id = "form:autocomplete")
    private AutoComplete autocomplete;
    
    @FindBy(id = "form:signature")
    private Signature signature;
    
    @FindBy(id = "form:selectBooleanCheckboxResult")
    private WebElement selectBooleanCheckboxResult;
    
    @FindBy(id ="form:selectManyCheckboxDatalist")
    private DataList selectManyCheckboxDatalist;
    
    @FindBy(id ="form:selectManyMenuDatatable")
    private DataTable selectManyMenuDatatable;
    
    @FindBy(id ="form:pickListDatalist")
    private DataList pickListDatalist;
    
    @FindBy(id = "form:open")
    private CommandButton button;
    
    @FindBy(id = "form:submit")
    private CommandButton submit;
    
    @FindBy(id = "form:dialog")
    private Dialog dialog;
    
    
    @FindBy(id ="form:tabview")
    private TabView tabView;
    
    @FindBy(id = "form:tabview:date")
    private WebElement date;
    
    @FindBy(id = "form:tabview:fileName")
    private WebElement fileName;
    
    @FindBy(id = "form:tabview:inline")
    private WebElement inline;
    
    @FindBy(id = "form:tabview:autocomplete")
    private WebElement autocompleteResult;
    
    @FindBy(id = "form:tabview:signatureResult")
    private Signature signatureResult;

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public SelectBooleanCheckbox getSelectBooleanCheckbox() {
		return selectBooleanCheckbox;
	}

	public void setSelectBooleanCheckbox(SelectBooleanCheckbox selectBooleanCheckbox) {
		this.selectBooleanCheckbox = selectBooleanCheckbox;
	}

	
	public PickList getPickList() {
		return pickList;
	}

	public void setPickList(PickList pickList) {
		this.pickList = pickList;
	}

	public SelectManyMenu getSelectManyMenu() {
		return selectManyMenu;
	}

	public void setSelectManyMenu(SelectManyMenu selectManyMenu) {
		this.selectManyMenu = selectManyMenu;
	}

	public SelectManyCheckbox getSelectManyCheckbox() {
		return selectManyCheckbox;
	}

	public void setSelectManyCheckbox(SelectManyCheckbox selectManyCheckbox) {
		this.selectManyCheckbox = selectManyCheckbox;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Inplace getInplaceWrapper() {
		return inplaceWrapper;
	}

	public void setInplaceWrapper(Inplace inplaceWrapper) {
		this.inplaceWrapper = inplaceWrapper;
	}

	public InputText getInplace() {
		return inplace;
	}

	public void setInplace(InputText inplace) {
		this.inplace = inplace;
	}

	public AutoComplete getAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(AutoComplete autocomplete) {
		this.autocomplete = autocomplete;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public CommandButton getButton() {
		return button;
	}

	public void setButton(CommandButton button) {
		this.button = button;
	}

	public CommandButton getSubmit() {
		return submit;
	}

	public void setSubmit(CommandButton submit) {
		this.submit = submit;
	}

	public Dialog getDialog() {
		return dialog;
	}

	public void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}

	public WebElement getSelectBooleanCheckboxResult() {
		return selectBooleanCheckboxResult;
	}

	public void setSelectBooleanCheckboxResult(WebElement selectBooleanCheckboxResult) {
		this.selectBooleanCheckboxResult = selectBooleanCheckboxResult;
	}

	public DataList getSelectManyCheckboxDatalist() {
		return selectManyCheckboxDatalist;
	}

	public void setSelectManyCheckboxDatalist(DataList selectManyCheckboxDatalist) {
		this.selectManyCheckboxDatalist = selectManyCheckboxDatalist;
	}

	public DataTable getSelectManyMenuDatatable() {
		return selectManyMenuDatatable;
	}

	public void setSelectManyMenuDatatable(DataTable selectManyMenuDatatable) {
		this.selectManyMenuDatatable = selectManyMenuDatatable;
	}

	public DataList getPickListDatalist() {
		return pickListDatalist;
	}

	public void setPickListDatalist(DataList pickListDatalist) {
		this.pickListDatalist = pickListDatalist;
	}

	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public WebElement getDate() {
		return date;
	}

	public void setDate(WebElement date) {
		this.date = date;
	}

	public WebElement getFileName() {
		return fileName;
	}

	public void setFileName(WebElement fileName) {
		this.fileName = fileName;
	}

	public WebElement getInline() {
		return inline;
	}

	public void setInline(WebElement inline) {
		this.inline = inline;
	}

	public WebElement getAutocompleteResult() {
		return autocompleteResult;
	}

	public void setAutocompleteResult(WebElement autocompleteResult) {
		this.autocompleteResult = autocompleteResult;
	}

	public Signature getSignatureResult() {
		return signatureResult;
	}

	public void setSignatureResult(Signature signatureResult) {
		this.signatureResult = signatureResult;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public void setDatePicker(DatePicker datePicker) {
		this.datePicker = datePicker;
	}
    
}
