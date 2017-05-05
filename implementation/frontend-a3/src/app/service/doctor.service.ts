import { Injectable } from '@angular/core';
import { Response } from '@angular/http';

import { HttpService } from './http.service';

import { Observable } from 'rxjs/Observable';
import { Observer } from 'rxjs/Observer';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

import { Doctor } from '../model/doctor';

@Injectable()
export class DoctorService {
  private doctorUrl: string = 'http://localhost:8080/clinic/api/doctor/';

  constructor(
    private httpService: HttpService
  ) { }

  getDoctors(): Observable<Doctor[]> {
    return this.httpService.get(this.doctorUrl)
      .map((res: Response) => res.json());
  }

  getDoctorById(id: number): Observable<Doctor> {
    return this.httpService.get(this.doctorUrl + 'id=' + id)
      .map((res: Response) => res.json());
  }

  updateDoctor(doctor: Doctor): Observable<Doctor> {
    return this.httpService.put(this.doctorUrl + 'id='+doctor.id, doctor)
      .map((res: Response) => res.json());
  }

}
