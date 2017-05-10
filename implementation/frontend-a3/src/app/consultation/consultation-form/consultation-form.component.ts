import { Component, OnInit, Input, ViewChild, Output, EventEmitter } from '@angular/core';

import { DoctorService } from '../../service/doctor.service';
import { Doctor, WorkingHour } from '../../model/doctor';
import { PatientService } from '../../service/patient.service';
import { Patient } from '../../model/patient';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';

@Component({
  selector: 'app-consultation-form',
  templateUrl: './consultation-form.component.html',
  styleUrls: ['./consultation-form.component.css'],
  outputs: ['errorEmitter'],
  providers: [
    ConsultationService,
    DoctorService,
    PatientService
  ]
})
export class ConsultationFormComponent implements OnInit {
  private consultation: any = {};
  @Input() private editable: boolean = true;
  @Output() errorEmitter: EventEmitter<boolean> = new EventEmitter(); // Emitter used to signal the parent component about a change

  @ViewChild('consultationForm') private form; // A reference to the form
  private error: boolean; // Whether the form contains an error or not (decided by validation logic)
  private messages: any = {}; // Error messages that will be displayed on the form

  @Input() set model(consultation: Consultation) {
    this.consultation = consultation;
    if (consultation.id == undefined) {
      this.patientService.getPatients().subscribe(patients => this.patients = patients);
      this.doctorService.getDoctors().subscribe(doctors => this.doctors = doctors);
    } else {
      this.startDate = new Date(consultation.startTime);
      this.startHour = this.startDate;
      this.duration = ((new Date(consultation.endTime)).getTime() - this.startDate.getTime()) / (60 * 1000);
      this.loadPatients();
      this.loadDoctors();
    }

  }

  private doctors: Doctor[];
  private doctor: Doctor;
  private patients: Patient[];
  private patient: Patient;

  private startDate: Date;
  private startHour: Date;
  private duration: number;
  private scheduleMsg: string;
  private consultations: string[];

  constructor(
    private consultationService: ConsultationService,
    private doctorService: DoctorService,
    private patientService: PatientService
  ) { }

  ngOnInit() {

    this.form.valueChanges
      .debounceTime(500)
      .subscribe(values => {
        if (this.startDate) {

          if (this.startHour == null ||
            this.startHour.getDay() != this.startDate.getDay() ||
            this.startHour.getMonth() != this.startDate.getMonth() ||
            this.startHour.getFullYear() != this.startDate.getFullYear()
          ) {
            this.startHour = this.startDate;
            this.startHour.setHours(0);
            this.startHour.setMinutes(0);
            this.startHour.setSeconds(0);
          }

          if (values.doctor) {
            this.consultation.doctor = values.doctor;
          }
          this.displayDoctorSchedule();
        }

        this.error = !this.checkInputs(values);
        this.errorEmitter.emit(this.error);
      });
  }

  private displayDoctorSchedule(): void {
    let dayOfWeek = this.startDate.getDay();
    if (dayOfWeek == 0) {
      dayOfWeek = 7;
    }

    let schedule = this.consultation.doctor.workingHours
      .filter(workingHour => workingHour.dayOfWeek == dayOfWeek);

    if (schedule.length == 0) {
      this.scheduleMsg = "The doctor is not available for this day";

    } else {
      this.scheduleMsg = "The doctor is available between ";
      schedule.map((workingHour: WorkingHour) => {
        this.scheduleMsg += workingHour.startHour + " - " + workingHour.endHour + ", ";
      });

      let dayString = this.startDate.getFullYear() + "-";

      if (this.startDate.getMonth() + 1 < 10) {
        dayString += "0" + (this.startDate.getMonth() + 1) + "-";
      } else {
        dayString += (this.startDate.getMonth() + 1) + "-";
      }

      if (this.startDate.getDate() < 10) {
        dayString += "0" + this.startDate.getDate();
      } else {
        dayString += this.startDate.getDate();
      }

      this.consultationService.getConsultationByDay(this.doctor.id, dayString).subscribe((consultations: Consultation[]) => {
        if (consultations != null) {
          this.consultations = [];

          consultations.forEach((consultation: Consultation) => {
            this.consultations.push(this.getInterval(consultation));
          });
        } else {
          this.consultations = null;
        }
      });
    }
  }

  private getInterval(consultation: Consultation): string {
    let start = "";
    let tmp = new Date(consultation.startTime).getHours();

    if (tmp < 10) {
      start += "0" + tmp + ":";
    } else {
      start += tmp + ":";
    }

    tmp = new Date(consultation.startTime).getMinutes();

    if (tmp < 10) {
      start += "0" + tmp;
    } else {
      start += tmp
    }

    let end = "";

    tmp = new Date(consultation.endTime).getHours();

    if (tmp < 10) {
      end += "0" + tmp + ":";
    } else {
      end += tmp + ":";
    }

    tmp = new Date(consultation.endTime).getMinutes();

    if (tmp < 10) {
      end += "0" + tmp;
    } else {
      end += tmp;
    }

    return (start + " - " + end);
  }

  private loadDoctors(): void {
    this.doctorService.getDoctors().subscribe(doctors => {
      this.doctor = this.consultation.doctor;

      let filtered = doctors.filter((doctor: Doctor) => doctor.id != this.doctor.id);
      filtered.push(this.doctor);
      this.doctors = filtered;
    });
  }

  private loadPatients(): void {
    this.patientService.getPatients().subscribe(patients => {
      this.patient = this.consultation.patient;

      let filtered = patients.filter((patient: Patient) => patient.id != this.patient.id);
      filtered.push(this.patient);
      this.patients = filtered;
    });
  }

  private syncData(): void {
    if (this.startHour) {
      this.consultation.startTime = this.startHour.getTime();
      this.consultation.endTime = this.startHour.getTime() + this.duration * 60 * 1000;
    }

    this.consultation.doctor = this.doctor;
    this.consultation.patient = this.patient;
  }

  private checkInputs(values): boolean {
    this.syncData();

    let result: boolean = true;

    if (this.patient == null) {
      result = false;
    }

    if (this.doctor == null) {
      result = false;
    }

    if (this.startHour == null) {
      result = false;
    }

    if (this.duration == null) {
      result = false;
    }

    return result;
  }

  public validateInputs(): boolean {
    return this.checkInputs(this.form.value);
  }
}
