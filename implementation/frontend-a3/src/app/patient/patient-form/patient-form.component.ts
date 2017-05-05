import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.component.css'],
  outputs: ['errorEmitter']
})
export class PatientFormComponent implements OnInit {
  private patient: any = {}; // The object where the data will be stored 
  @Input() private editable: boolean = true; // Whether the form is editable or not (accesed as an input by using a setter)
  @Output() errorEmitter: EventEmitter<boolean> = new EventEmitter(); // Emitter used to signal the parent component about a change

  @ViewChild('patientForm') private form; // A reference to the form
  private error: boolean; // Whether the form contains an error or not (decided by validation logic)
  private messages: any = {}; // Error messages that will be displayed on the form

  // Setter for the patient object
  @Input() set model(patient: any) {
    this.patient = patient;
  }

  constructor() { }

  ngOnInit() {
    this.form.valueChanges
      .debounceTime(500)
      .subscribe(values => {

        this.error = !this.checkInputs(values);
        this.errorEmitter.emit(this.error);
      });
  }

  private checkInputs(values): boolean {
    let result: boolean = true;

    if (this.validateFullName(values.fullName) == false) {
      result = false;
    }

    if (this.validatePnc(values.personalNumericalCode) == false) {
      result = false;
    }

    if (this.validateIdSer(values.idCardSeries) == false) {
      result = false;
    }

    if (this.validateIdNr(values.idCardNumber) == false) {
      result = false;
    }

    if (values.address == null) {
      result = false;
    }

    return result;
  }

  private validateFullName(fullName): boolean {
    if (fullName == null || fullName == "") {
      this.messages.fullName = null;
      return false;
    } else {
      if (fullName.match(/^[a-zA-Z- ]+$/) == null) {
        this.messages.fullName = "Error: Full name should contain only alphabetic characters and spaces";
        return false;
      } else {
        this.messages.fullName = null;
        return true;
      }
    }
  }

  validatePnc(pnc: string): boolean {
    if (pnc == null || pnc == "") {
      this.messages.pnc = null;
      return false;
    } else {
      if (pnc.length != 13 || pnc.match(/^[0-9]+$/) == null) {
        this.messages.pnc = "Error: Personal Numerical Codes must have 13 digits";
        return false;
      } else {
        this.messages.pnc = null;
        return true;
      }
    }
  }

  validateIdSer(id_ser: string): boolean {
    if (id_ser == null || id_ser == "") {
      this.messages.id_ser = null;
      return false;
    } else {
      if (id_ser.length != 2 || id_ser.match(/^[a-zA-Z]+$/) == null) {
        this.messages.id_ser = "Error: ID Card Series must have 2 alphabetic characters";
        return false;
      } else {
        this.messages.id_ser = null;
        return true;
      }
    }
  }

  // Returns true if the provided identity card number is valid. Also sets the error messages accordingly
  validateIdNr(id_nr: string): boolean {
    if (id_nr == null || id_nr == "") {
      this.messages.id_nr = null;
      return false;
    } else {
      if (id_nr.length != 6 || id_nr.match(/^[0-9]+$/) == null) {
        this.messages.id_nr = "Error: ID Card Number must have 6 digits";
        return false;
      } else {
        this.messages.id_nr = null;
        return true;
      }
    }
  }

  public validateInputs(): boolean {
    return this.checkInputs(this.form.value);
  }

}
