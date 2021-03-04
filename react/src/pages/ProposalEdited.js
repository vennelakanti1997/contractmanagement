import React from 'react'
import SupplierNavbar from '../component/AdminNavbar'
import { proposalQuotation, proposalDate, proposalId } from './EditProposal';
import { Link } from 'react-router-dom'

function ProposalEdited() {
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
                                        Proposal Details Changed Successfully</button>
                                </h3>
                                <div id="collapseOne"
                                    class="accordion-collapse collapse show"
                                    aria-labelledby="headingOne"
                                    data-bs-parent="#accordionExample">
                                    <div class="accordion-body">
                                        Proposal Id:<strong> {proposalId}</strong><br></br>
                                Proposal Date:<strong> {proposalDate}</strong><br></br>
                                Proposal Quotation:<strong> {proposalQuotation}</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <Link to="/viewsupplierproposals" className="link"><h6>View Your Proposals</h6></Link>
                    </div >
                </div>
            ) : (<h1>You are not Authorized</h1>)}

        </div>
    )
}

export default ProposalEdited
