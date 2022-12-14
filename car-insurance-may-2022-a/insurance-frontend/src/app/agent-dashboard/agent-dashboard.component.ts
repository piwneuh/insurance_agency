import { Component, OnInit } from '@angular/core';
import { faFile, faFileCirclePlus, faFileCircleQuestion } from '@fortawesome/free-solid-svg-icons';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-agent-dashboard',
  templateUrl: './agent-dashboard.component.html',
  styleUrls: ['./agent-dashboard.component.css'],
})
export class AgentDashboardComponent implements OnInit {
  faFileCirclePlus = faFileCirclePlus;
  faFile = faFile;
  faFileCricleQuestion = faFileCircleQuestion;

  constructor(
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {}
}
