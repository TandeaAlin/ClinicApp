import { Component, OnInit } from '@angular/core';

import { AuthService } from '../../service/auth.service';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';

@Component({
  selector: 'app-upcoming-consultation-list',
  templateUrl: './upcoming-consultation-list.component.html',
  styleUrls: ['./upcoming-consultation-list.component.css'],
  providers: [
    ConsultationService
  ]
})
export class UpcomingConsultationListComponent implements OnInit {
  private consultations: Consultation[];

  constructor(
    private consultationService: ConsultationService
  ) { }

  ngOnInit() {
    this.loadConsultationData();
  }

  private loadConsultationData(): void {
    this.consultationService.getUpcomingConsultations().subscribe(consultations => {
      this.consultations = consultations;
    });
  }

}
