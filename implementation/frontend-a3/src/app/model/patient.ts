export class Patient {
    id: number;
    fullName: string;
    idCardSeries: string;
    idCardNumber: string;
    personalNumericalCode: string;
    address: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
