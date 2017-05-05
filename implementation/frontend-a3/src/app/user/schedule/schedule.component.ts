import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DoctorService } from '../../service/doctor.service';
import { Doctor, WorkingHour } from '../../model/doctor';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css'],
  providers: [
    DoctorService
  ]
})
export class ScheduleComponent implements OnInit {

  private days = ['Mon', 'Tue', 'Weed', 'Thu', 'Fri', 'Sat', 'Sun']
  private matrix;
  private doctor: Doctor;

  constructor(
    private route: ActivatedRoute,
    private doctorService: DoctorService
  ) {
    this.initMatrix();
  }

  ngOnInit() {
    this.loadDoctorData();
  }

  private initMatrix(): void {
    this.matrix=[];
    for (let hour = 0; hour < 24; hour++) {
      let col = [];
      for (let day = 1; day < 8; day++) {
        col.push(false);
      }
      this.matrix.push(col);
    }
  }

  private submit(): void{
    let schedule = this.matrixToSchedule();
    this.doctor.workingHours = schedule;
    this.doctorService.updateDoctor(this.doctor)
      .subscribe(
          client => {
            console.log("Done");
          },
          err => {
            console.log(err);
          });
  }

  private loadDoctorData(): void {
    this.route.params.subscribe(params => {
      let id = params['id'];

      this.doctorService.getDoctorById(id)
        .subscribe((doctor: Doctor) => {
          this.doctor=doctor;
          this.initMatrix();
          this.scheduleToMatrix();
        });
    });
  }

  private toggle(i: number, j: number): void {
    this.matrix[i][j] = !this.matrix[i][j];
  }

  private scheduleToMatrix() {
    this.doctor.workingHours.forEach(workingHour => {
      for (let hour = workingHour.startHour; hour < workingHour.endHour; hour++) {
        this.matrix[hour][workingHour.dayOfWeek - 1] = true;
      }
    })
  }

  private matrixToSchedule() {
    let schedule = [];
    let workingHour: WorkingHour;

    for (let day = 0; day < 7; day++) {
      let workingHours = [];

      for (let hour = 0; hour < 24; hour++) {
        if (this.matrix[hour][day]) {
          workingHours.push(hour);
        }
      }

      for (let i = 0; i < workingHours.length; i++) {
        let isStart = false;
        let isEnd = false;

        if (i == 0) {
          isStart = true;
        } else {
          if (workingHours[i - 1] != workingHours[i] - 1) {
            isStart = true;
          }
        }

        if (i == workingHours.length - 1) {
          isEnd = true;
        } else {
          if (workingHours[i + 1] != workingHours[i] + 1) {
            isEnd = true;
          }
        }

        if (isStart) {
          workingHour = new WorkingHour();
          workingHour.dayOfWeek = day+1;
          workingHour.startHour = workingHours[i];
        }

        if (isEnd) {
          workingHour.endHour = workingHours[i] + 1;
          schedule.push(workingHour);
        }

      }

    }

    return schedule;
  }

}