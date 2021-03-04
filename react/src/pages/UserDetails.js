import React from 'react'
import { supplierId, supplierName, supplierContactNumber, supplierAddress, supplierType } from './Signup';
import { Link } from 'react-router-dom'
import HomeNavbar from '../component/HomeNavbar';
import './UserDetails.css'

function UserDetails() {
    return (
        <div>
            <HomeNavbar />
            <div className="container hero">
                <div class="accordion" id="accordionExample">
                    <div class="accordion-item">
                        <h2 class="accordion-header" id="headingOne">
                            <button class="accordion-button"
                                type="button"
                                data-bs-toggle="collapse"
                                data-bs-target="#collapseOne"
                                aria-expanded="true"
                                aria-controls="collapseOne">
                                Your details below</button>
                        </h2>
                        <div id="collapseOne"
                            class="accordion-collapse collapse show"
                            aria-labelledby="headingOne"
                            data-bs-parent="#accordionExample">
                            <div class="accordion-body">
                                <strong>ID: </strong>{supplierId}<br></br>
                                <strong>Name: </strong>{supplierName}<br></br>
                                <strong>Contact number: </strong>{supplierContactNumber}<br></br>
                                <strong>Address: </strong>{supplierAddress}<br></br>
                                <strong>Supplier type: </strong>{supplierType}
                            </div>
                        </div>
                    </div>
                </div>


                <Link to="/login/supplier" className="link">Back to login</Link>
            </div >
        </div>
    )
}

export default UserDetails
