import React from 'react'
import { requirementDeliveryDate, requirementType, requirementDescription } from './CreateRequirement';
import AdminNavbar from '../component/AdminNavbar'
import { Link } from 'react-router-dom'

function RequirementDetails() {
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
                                        Successfully Created Requirement. Details below:</button>
                                </h3>
                                <div id="collapseOne"
                                    class="accordion-collapse collapse show"
                                    aria-labelledby="headingOne"
                                    data-bs-parent="#accordionExample">
                                    <div class="accordion-body">
                                        Description: <strong>{requirementDescription} </strong><br></br>
                            Delivery Date:<strong> {requirementDeliveryDate}</strong><br></br>
                            Requirement type:<strong>{requirementType}</strong>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <Link to="/viewrequirements" className="link"><h6>View Requirements Created</h6></Link>
                    </div >
                </div>
            ) : (
                    <h1>You are not Authorized</h1>
                )}

        </div>
    )
}

export default RequirementDetails