import { User } from './user';

export class Doctor extends User{
    workingHours: WorkingHour[];
}

export class WorkingHour{
    startHour: number;
    endHour: number;
    dayOfWeek: number;
}
