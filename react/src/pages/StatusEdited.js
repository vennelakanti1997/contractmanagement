import React from 'react'
import AdminNavbar from '../component/AdminNavbar'
import { proposalStatus, proposalId } from './ViewProposals';
import { Link } from 'react-router-dom'

function ProposalDetails() {
    const adminData = JSON.parse(localStorage.getItem('info'))

    return (
        <div>
            {adminData.role === "admin" ? (
                <div>
                    <AdminNavbar />
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
                                        Status Changed Successfully</button>
                                </h3>
                                <div id="collapseOne"
                                    class="accordion-collapse collapse show"
                                    aria-labelledby="headingOne"
                                    data-bs-parent="#accordionExample">
                                    <div class="accordion-body">

                                        Proposal Id:<strong> {proposalId}</strong><br></br>
                                Proposal Status:<strong> {proposalStatus}</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <Link to="/viewproposaladmin" className="link"><h6>View Proposals</h6></Link>
                    </div >
                </div>) : (<h1>You are not Authorized</h1>)
            }
        </div>
    )
}

export default ProposalDetails