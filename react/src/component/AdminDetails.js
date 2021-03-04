import React, { useState } from 'react'
import axios from 'axios';
import AdminNavbar from './AdminNavbar';


function capitalizeFirstLetter(word) {
    return word.charAt(0).toUpperCase() + word.slice(1);
}

function AdminDetails() {
    const adminData = JSON.parse(localStorage.getItem('info'))
    const [adminName, setAdminName] = useState();

    axios.get('http://localhost:8080/auth/adminvalidate', {
        headers: {
            'Authorization': `Bearer ${adminData.token}`
        }
    })
        .then(res => {
            setAdminName(capitalizeFirstLetter(res.data.name))
        })
        .catch(err => {
            console.log(err)
        })
    return (
        <div>
            {adminData.role === "admin" ? (
                <div>
                    <AdminNavbar />
                    <div class="card text-center">
                        <div class="card-header">
                            <h1>Welcome Mr. {adminName}</h1>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">Your ID: <strong>{adminData.id}</strong></h5>
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

export default AdminDetails
