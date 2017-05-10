import { Consultation } from './consultation';

export class Notification {
    id: number;
    seen: boolean;
    text: string;
    consultation: Consultation;
    createdAt: Date;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
