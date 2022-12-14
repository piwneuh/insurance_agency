import { Component, OnInit } from '@angular/core';
import { faFileCircleCheck, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { Proposal } from '../model/proposal';
import { ProposalService } from '../services/proposal.service';

@Component({
  selector: 'app-proposal-list',
  templateUrl: './proposal-list.component.html',
  styleUrls: ['./proposal-list.component.css']
})
export class ProposalListComponent implements OnInit {

  constructor(
    private proposalService: ProposalService,
  ) { }

  currentProposal: any;
  proposals: Proposal[];
  backupProposals: Proposal[];

  faFileCheck = faFileCircleCheck;
  faMagnifyingGlass = faMagnifyingGlass;

  
  ngOnInit(): void {
    this.getAllProposals();
    this.currentProposal = localStorage.getItem('proposalId');
  }

  getAllProposals(){
    this.proposalService.getAll().subscribe(f => { this.proposals = f;
       this.backupProposals = JSON.parse(JSON.stringify(this.proposals));
    })
  }
   
  setActive(id: number){
    if(this.currentProposal === id){
      this.currentProposal = -1;
      localStorage.removeItem('proposalId');
    }
    else{
      this.currentProposal = id;
      localStorage.setItem('proposalId', JSON.stringify(id));
    }
  }
  
  onKey(event : any) { 
    this.proposals = this.search(event.target.value);
  }

  search(value : string) { 
    this.proposals = JSON.parse(JSON.stringify(this.backupProposals))
    let filter = value.toLowerCase();
    return this.proposals.filter(p => p.subscriber.firstName.toLowerCase().startsWith(filter) || 
                                      p.subscriber.lastName.toLowerCase().startsWith(filter) || 
                                      p.id.toString() == filter ||
                                      p.subscriber.jmbg.startsWith(filter) ||
                                      p.creationDate.toString().startsWith(filter));
  }

}
