import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import Signup from './pages/Signup';
import UserDetails from './pages/UserDetails';
import SupplierDetails from './component/SupplierDetails';
import AdminDetails from './component/AdminDetails';
import { CreateContract } from './component/CreateContract';
import { ViewContractNew } from './component/VewContractNew';
import { EditContract } from './component/EditContract';
import { ManageDelivery } from './component/ManageDelivery';
import { SetAmenities } from './component/SetAmenities';
import Login from './pages/Login';
import { EnterAmenities } from './component/EnterAmenities';
import ProposalDetails from './pages/ProposalDetails';
import CreateRequirement from './pages/CreateRequirement';
import RequirementDetails from './pages/RequirementDetails';
import ViewRequirementSupplier from './pages/ViewRequirementSupplier'
import ViewRequirements from './pages/ViewRequirements'
import CreateProposal from './pages/CreateProposal'
import ViewProposalAdmin from './pages/ViewProposalAdmin'
import ViewProposals from './pages/ViewProposals'
import StatusEdited from './pages/StatusEdited'
import ViewSupplierProposals from './pages/ViewSupplierProposals'
import ViewRevisitedProposals from './pages/ViewRevisitedProposals'
import EditProposal from './pages/EditProposal'
import ProposalEdited from './pages/ProposalEdited';
import { ListSuppliers } from './component/ListSuppliers';
import { ListSupplierContracts } from './component/ListSupplierContracts';
import { ListActiveContracts } from './component/ListActiveContracts';
import ListProposalReport from './component/ListProposalReport';
import ViewRequirementsAdmin from './pages/ViewRequirementsAdmin';

function App() {
  return (
    <div>
      <Router>
        <Switch>
          {/* Add By Madhurya */}
          <Route path="/" exact component={Home} />
          <Route path="/login/:role" component={Login} />
          <Route path="/signup" exact component={Signup} />
          <Route path="/userdetails" exact component={UserDetails} />
          <Route path="/supplierdetails" exact component={SupplierDetails} />
          <Route path="/admindetails" exact component={AdminDetails} />
          {/* Add by Bhargav */}
          <Route path="/createcontract" exact component={CreateContract} />
          <Route path="/viewcontracts" exact component={ViewContractNew}></Route>
          <Route path="/edit/:cid" exact component={EditContract}></Route>
          <Route path="/managedelivery" exact component={ManageDelivery} />
          <Route path='/amenities' exact component={SetAmenities} />
          <Route path="/enteramenities/:cid" exact component={EnterAmenities} />
          <Route path="/listsuppliers" exact component={ListSuppliers} />
          <Route path="/listcontracts" exact component={ListSupplierContracts} />
          <Route path="/listactivecontracts" exact component={ListActiveContracts} />
          {/* Add by Pratyush */}
          <Route path="/listproposalreport" exact component={ListProposalReport} />
          <Route path="/createrequirement" exact component={CreateRequirement} />
          <Route path="/requirementdetails" component={RequirementDetails} />
          <Route path="/proposaldetails" component={ProposalDetails} />
          <Route path="/viewreqsupplier" exact component={ViewRequirementSupplier} />
          <Route path="/viewreqs/:rid" exact component={ViewRequirements} />
          <Route path="/createproposal/:rid" exact component={CreateProposal} />
          <Route path="/viewproposaladmin" exact component={ViewProposalAdmin} />
          <Route path="/viewproposals/:pid" exact component={ViewProposals} />
          <Route path="/statusedited" exact component={StatusEdited} />
          <Route path="/viewsupplierproposals" exact component={ViewSupplierProposals} />
          <Route path="/viewrevisitedproposals" exact component={ViewRevisitedProposals} />
          <Route path="/editproposal/:pid" exact component={EditProposal} />
          <Route path="/proposaledited" exact component={ProposalEdited} />
          <Route path="/viewrequirements" exact component={ViewRequirementsAdmin} />
        </Switch>
      </Router>
    </div>

  );
}

export default App;

