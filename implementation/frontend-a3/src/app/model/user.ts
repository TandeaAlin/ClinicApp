import { Role } from './role';

export class User {
    id: number;
    username: string;
    fullName: string;
    address: string;
    roles: Role[];

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
