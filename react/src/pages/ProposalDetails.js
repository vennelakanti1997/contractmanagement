import React from 'react'
import { proposalRequirementId, proposalQuotation, proposalDate } from './CreateProposal';
import SupplierNavbar from '../component/SupplierNavbar'
import { Link } from 'react-router-dom'

function ProposalDetails() {
    const supplierData = JSON.parse(localStorage.getItem('info'))
    return (
        <div>
            {supplierData.role === "supplier" ? (
                <div>
                    <SupplierNavbar />
                    <div className="container hero">
                        <div class="accordion" id="accordionExample">
                            <div class="accordion-item">
                                <h3 class="accordion-header" id="headingOne">
                                    <button class="accordion-button"
                                        type="button"
                                        data-bs-toggle="collapse"
                                        data-bs-target="#collapseOne"
                                        aria-expanded="true"
                                        aria-controls="collapseOne">
                                        Successfully Submitted Proposal. Details below:</button>
                                </h3>
                                <div id="collapseOne"
                                    class="accordion-collapse collapse show"
                                    aria-labelledby="headingOne"
                                    data-bs-parent="#accordionExample">
                                    <div class="accordion-body">
                                        Quotation:<strong> {proposalQuotation}</strong><br></br>
                                Proposal Date:<strong> {proposalDate}</strong><br></br>
                                RequirementId:<strong> {proposalRequirementId}</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <Link to="/viewsupplierproposals" className="link"><h6>View My Proposals</h6></Link>
                    </div >
                </div>
            ) : (<h1>You are not Authorized</h1>)}

        </div>
    )
}

export default ProposalDetails