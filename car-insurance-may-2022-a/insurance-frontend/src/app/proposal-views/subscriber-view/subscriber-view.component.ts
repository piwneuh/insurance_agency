import { Component, Input, OnInit } from '@angular/core';
import { Subscriber } from '../../model/subscriber.model';

@Component({
  selector: 'app-subscriber-view',
  templateUrl: './subscriber-view.component.html',
  styleUrls: ['./subscriber-view.component.css'],
})
export class SubscriberViewComponent implements OnInit {
  @Input() subscriber: Subscriber;

  constructor() {}

  ngOnInit(): void {}
}
