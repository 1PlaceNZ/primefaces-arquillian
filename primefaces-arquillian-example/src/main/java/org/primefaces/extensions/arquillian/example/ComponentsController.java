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

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.extensions.arquillian.example.entity.Car;
import org.primefaces.model.DualListModel;
import org.primefaces.model.file.UploadedFile;

@Named
@ViewScoped
public class ComponentsController implements Serializable {
    
	boolean selectBooleanCheckbox;
	
	List<Car> selectedManyMenu = new ArrayList<Car>();
	
	List<Car> selectedManyMenuList = new ArrayList<Car>();
	
	List<Car> selectManyCheckbox = new ArrayList<Car>();
	
	List<Car> selectManyCheckboxList = new ArrayList<Car>();
	
	 private DualListModel<Car> pickList;
	 
	 Date calendar;
	 
	 private LocalDate datePicker;
	 
	 String inplace;
	 
	 Car autocomplete;
	 
	 private UploadedFile file;
	 
	 String signature;
	 
	 List<Car> allCars =  new ArrayList<Car>();
	 
	 boolean selectBooleanCheckboxResult;
	 
	 List<Car> selectManyCheckboxDatalist;
	 
	 List<Car> selectManyMenuDatatable;
	 
	 List<Car> pickListDatalist;
	 
	 String fileName;
	
	@PostConstruct
	public void setup() {
		selectedManyMenuList.add(new Car(1l,"BMW"));
		selectedManyMenuList.add(new Car(2l,"VW"));
		selectedManyMenuList.add(new Car(3l,"Mercedes"));
		
		selectManyCheckboxList.add(new Car(4l,"Porsche"));
		selectManyCheckboxList.add(new Car(5l,"Ferrari"));
		selectManyCheckboxList.add(new Car(6l,"Aston Martin"));
		
		
		List<Car> carSource =  new ArrayList<Car>();
		carSource.add(new Car(7l,"Ford"));
		carSource.add(new Car(8l,"Mazda"));
		carSource.add(new Car(9l,"Toyato"));
		carSource.add(new Car(10l,"Honda"));
		
        List<Car> carTarget = new ArrayList<Car>();
         
        pickList = new DualListModel<Car>(carSource, carTarget);
		
        
        allCars.add(new Car(1l,"Car 1"));
        allCars.add(new Car(2l,"Car 2"));
        allCars.add(new Car(3l,"Car 3"));
        allCars.add(new Car(4l,"Car 4"));
        allCars.add(new Car(5l,"Car 5"));
        allCars.add(new Car(6l,"Car 6"));
	}
	
	
	public List<Car> autocompleteMethod(String query) {
        List<Car> filteredCar = new ArrayList<Car>();
         
        for (int i = 0; i < allCars.size(); i++) {
        	Car car = allCars.get(i);
            if(car.getName().toLowerCase().contains(query.toLowerCase())) {
            	filteredCar.add(car);
            }
        }
        return filteredCar;
    }
     
	
	


	public List<Car> getSelectedManyMenu() {
		return selectedManyMenu;
	}


	public void setSelectedManyMenu(List<Car> selectedManyMenu) {
		this.selectedManyMenu = selectedManyMenu;
	}


	public List<Car> getSelectedManyMenuList() {
		return selectedManyMenuList;
	}


	public void setSelectedManyMenuList(List<Car> selectedManyMenuList) {
		this.selectedManyMenuList = selectedManyMenuList;
	}


	public List<Car> getSelectManyCheckbox() {
		return selectManyCheckbox;
	}


	public void setSelectManyCheckbox(List<Car> selectManyCheckbox) {
		this.selectManyCheckbox = selectManyCheckbox;
	}


	public List<Car> getSelectManyCheckboxList() {
		return selectManyCheckboxList;
	}


	public void setSelectManyCheckboxList(List<Car> selectManyCheckboxList) {
		this.selectManyCheckboxList = selectManyCheckboxList;
	}


	public DualListModel<Car> getPickList() {
		return pickList;
	}


	public void setPickList(DualListModel<Car> pickList) {
		this.pickList = pickList;
	}


	public Date getCalendar() {
		return calendar;
	}


	public void setCalendar(Date calendar) {
		this.calendar = calendar;
	}


	public String getInplace() {
		return inplace;
	}


	public void setInplace(String inplace) {
		this.inplace = inplace;
	}


	public Car getAutocomplete() {
		return autocomplete;
	}


	public void setAutocomplete(Car autocomplete) {
		this.autocomplete = autocomplete;
	}


	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
	}


	public List<Car> getAllCars() {
		return allCars;
	}


	public void setAllCars(List<Car> allCars) {
		this.allCars = allCars;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public boolean isSelectBooleanCheckbox() {
		return selectBooleanCheckbox;
	}


	public void setSelectBooleanCheckbox(boolean selectBooleanCheckbox) {
		this.selectBooleanCheckbox = selectBooleanCheckbox;
	}


	public String submit() {
		selectBooleanCheckboxResult = selectBooleanCheckbox;
		selectManyMenuDatatable = selectedManyMenu;
		selectManyCheckboxDatalist = selectManyCheckbox;
		pickListDatalist = pickList.getTarget();
		if (file!=null) {
			fileName = file.getFileName();
		}
		return "";
	}


	public boolean getSelectBooleanCheckboxResult() {
		return selectBooleanCheckboxResult;
	}


	public void setSelectBooleanCheckboxResult(boolean selectBooleanCheckboxResult) {
		this.selectBooleanCheckboxResult = selectBooleanCheckboxResult;
	}


	public List<Car> getSelectManyCheckboxDatalist() {
		return selectManyCheckboxDatalist;
	}


	public void setSelectManyCheckboxDatalist(List<Car> selectManyCheckboxDatalist) {
		this.selectManyCheckboxDatalist = selectManyCheckboxDatalist;
	}


	public List<Car> getSelectManyMenuDatatable() {
		return selectManyMenuDatatable;
	}


	public void setSelectManyMenuDatatable(List<Car> selectManyMenuDatatable) {
		this.selectManyMenuDatatable = selectManyMenuDatatable;
	}


	public List<Car> getPickListDatalist() {
		return pickListDatalist;
	}


	public void setPickListDatalist(List<Car> pickListDatalist) {
		this.pickListDatalist = pickListDatalist;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public LocalDate getDatePicker() {
		return datePicker;
	}


	public void setDatePicker(LocalDate datePicker) {
		this.datePicker = datePicker;
	}
}
