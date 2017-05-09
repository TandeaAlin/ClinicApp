import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from './http.service';

import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Consultation } from '../model/consultation';
import { Patient } from '../model/patient';

@Injectable()
export class ConsultationService {
  private consultationUrl: string = 'http://localhost:8080/clinic/api/consultation/';

  constructor(
    private httpService: HttpService
  ) { }

  getConsultations(): Observable<Consultation[]> {
    return this.httpService.get(this.consultationUrl)
      .map((res: Response) => res.json());
  }

  getConsultationById(id: number): Observable<Consultation> {
    return this.httpService.get(this.consultationUrl + 'id=' + id)
      .map((res: Response) => res.json());
  }

  // The day should be in ISO8601 format
  getConsultationByDay(day: string): Observable<Consultation[]> {
    return this.httpService.get(this.consultationUrl + 'day=' + day)
      .map((res: Response) => res.json());
  }

  getUpcomingConsultations(): Observable<Consultation[]> {
    return this.httpService.get(this.consultationUrl + 'upcoming')
      .map((res: Response) => res.json());
  }

  getConsultationsByPatient(patient: Patient): Observable<Consultation[]> {
    return this.httpService.get(this.consultationUrl + 'patientId=' + patient.id)
      .map((res: Response) => res.json());
  }

  createConsultation(consultation: Object): Observable<Consultation> {
    return this.httpService.post(this.consultationUrl, consultation)
      .map((res: Response) => res.json());
  }

  updateConsultation(consultation: Consultation): Observable<Consultation> {
    return this.httpService.put(this.consultationUrl + consultation.id, consultation)
      .map((res: Response) => res.json());
  }

  deleteConsultation(id: number) {
    return this.httpService.delete(this.consultationUrl + id)
      .map((res: Response) => res);
  }
}
