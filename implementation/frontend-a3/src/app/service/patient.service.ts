import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from './http.service';

import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Patient } from '../model/patient';

@Injectable()
export class PatientService {
  private patientsUrl: string = 'http://localhost:8080/clinic/api/patient/';

  constructor(
    private httpService: HttpService
  ) { }

  getPatients(): Observable<Patient[]> {
    return this.httpService.get(this.patientsUrl)
      .map((res: Response) => res.json());
  }

  getPatientById(id: number): Observable<Patient> {
    return this.httpService.get(this.patientsUrl + 'id=' + id)
      .map((res: Response) => res.json());
  }

  getPatientByPnc(pnc: string): Observable<Patient> {
    return this.httpService.get(this.patientsUrl + 'pnc=' + pnc)
      .map((res: Response) => res.json());
  }

  createPatient(patient: Object): Observable<Patient> {
    return this.httpService.post(this.patientsUrl, patient)
      .map((res: Response) => res.json());
  }

  updatePatient(patient: Patient): Observable<Patient> {
    return this.httpService.put(this.patientsUrl + 'id=' + patient.id, patient)
      .map((res: Response) => res.json());
  }

  deletePatient(id: number) {
    return this.httpService.delete(this.patientsUrl + id)
      .map((res: Response) => res);
  }
}
