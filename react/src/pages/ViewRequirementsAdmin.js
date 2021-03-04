import React from "react";
import AdminNavbar from '../component/AdminNavbar';
import { Link } from 'react-router-dom';


const adminDataInfo = JSON.parse(localStorage.getItem('info'))

export default class ViewRequirementsAdmin extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            requirementids: null,
            emptyData: false,
            activePage: 1
        }

    }

    handlePageChange(pageNumber) {
        console.log(`active page is ${pageNumber}`);
        this.setState({ activePage: pageNumber });
    }


    componentDidMount() {
        const adminData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/requirement/viewall`, {
            headers: {
                'Authorization': `Bearer ${adminData.token}`
            }
        }).then((res) => {

            if (res.status === 200) {
                return res.json();
            }
            else if (res.status === 400) {
                this.setState({ emptyData: true },)

                throw new Error("No Data Found")
            }

        }).then((res) => {

            this.setState({ requirementids: res })
        }).catch((e) => console.log(e))

    }

    render() {
        return (
            <div>
                {adminDataInfo.role === "admin" ? (
                    <div>
                        <AdminNavbar />
                        {this.state.emptyData === true ? (

                            <div className="container">
                                <div class="col-md-6 offset-md-3">
                                    <br />
                                    <br />
                                    <br />
                                    <br />
                                    <h3><strong>No Requirements Created Yet</strong></h3>
                                    <br />
                                    <br />

                                    <Link to="/createrequirement" >Create Requirement</Link>
                                </div>
                            </div>
                        ) :
                            <div class="row">
                                <div class="col-md-6 offset-md-3">
                                    <br /><br />
                                    <h3> Requirements </h3><br />
                                    <table class="table table-bordered">
                                        <tr>

                                            <th>Requirement ID</th>
                                            <th>Requirement Description</th>
                                            <th>Requirement Delivery Date</th>
                                            <th>Requirement Type</th>
                                        </tr>
                                        {
                                            this.state.requirementids ?
                                                this.state.requirementids && this.state.requirementids.map((requirement, i) =>
                                                    <tr>
                                                        <td>{requirement.id}</td>
                                                        <td> {requirement.description}</td>
                                                        <td>{requirement.deliveryDate}</td>
                                                        <td>{requirement.type}</td>
                                                    </tr>
                                                )
                                                :
                                                null
                                        }
                                    </table>

                                    <br />

                                    <br />
                                    <br />
                                    <Link to="/createrequirement" ><h5>Create New Requirement</h5></Link>
                                </div>
                            </div>
                        }
                    </div>
                ) : (<h1>You are not Authorized</h1>)}

            </div>
        )
    }
}
