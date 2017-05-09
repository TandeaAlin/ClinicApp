import { Patient } from './patient';
import { Doctor }  from './doctor';

export class Consultation {
    id: number;
    patient: Patient;
    doctor: Doctor;
    observations: Observation[];
    startTime: Date;
    endTime: Date;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}

export class Observation{
    id: number;
    text: string;
    createdAt: Date;
}
