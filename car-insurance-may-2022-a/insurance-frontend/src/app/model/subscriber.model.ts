import { Person } from './person.model';
import { SubscriberRole } from './subscriber-role.model';

export class Subscriber extends Person {
  subscriberRole: SubscriberRole;

  constructor() {
    super();
    this.subscriberRole = new SubscriberRole();
  }
}
