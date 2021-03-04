import React, { useState } from 'react'
import axios from 'axios';
import SupplierNavbar from './SupplierNavbar';

function SupplierDetails() {
    const supplierData = JSON.parse(localStorage.getItem('info'))
    const [supplierName, setSupplierName] = useState();

    axios.get('http://localhost:8080/auth/suppliervalidate', {
        headers: {
            'Authorization': `Bearer ${supplierData.token}`
        }
    })
        .then(res => {
            setSupplierName(res.data.name)
        })
        .catch(err => {
            console.log(err)
        })
    return (
        <div>
            {supplierData.role === "supplier" ?
                (
                    <div>
                        <SupplierNavbar />
                        <div class="card text-center">
                            <div class="card-header">
                                <h1> Welcome <strong>{supplierName}</strong></h1>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">Your ID: <strong>{supplierData.id}</strong></h5>
                                <div class="alert alert-primary" role="alert">
                                    Click the Hamburger
                        </div>
                            </div>
                        </div>
                    </div>
                ) : (
                    <h1>You are not Authorized</h1>
                )}

        </div>

    )
}

export default SupplierDetails
