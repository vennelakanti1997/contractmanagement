import React from 'react'
import AdminNavbar from '../component/AdminNavbar';
import { Link } from 'react-router-dom'


const adminData = JSON.parse(localStorage.getItem('info'))

export default class ViewProposalAdmin extends React.Component {


    constructor() {
        super();
        this.state = {
            proposals: null,
            emptyData: false
        }

    }

    componentDidMount() {
        const adminData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/proposal/viewall`, {
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

            this.setState({ proposals: res })
        }).catch((e) => console.log(e))

    }

    render() {
        return (
            <div>
                {adminData.role === "admin" ? (
                    <div>

                        <AdminNavbar />
                        {this.state.emptyData === true ? (

                            <div className="container">
                                <div class="col-md-6 offset-md-3">
                                    <br />
                                    <br />
                                    <br />
                                    <br />
                                    <h3><strong>No New Proposals Submitted</strong></h3>
                                    <br />
                                    <br />

                                </div>
                            </div>
                        ) :
                            <div class="row">
                                <div class="col-md-6 offset-md-3">
                                    <br /><br />
                                    <h3>Submitted Proposals </h3><br />
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Proposal ID</th>
                                            <th>Requirement ID</th>
                                            <th>Supplier Name</th>
                                            <th></th>
                                        </tr>
                                        {
                                            this.state.proposals ?
                                                this.state.proposals && this.state.proposals.map((proposal, i) =>
                                                    <tr>
                                                        <td>{proposal.id}</td>
                                                        <td>  {proposal.requirementId}</td>
                                                        <td> {proposal.supplierName}</td>
                                                        <td><Link to={{
                                                            pathname: "/viewproposals/" + proposal.id, state: {
                                                                proposalid: this.props.pid
                                                            }
                                                        }}>View Details</Link></td>
                                                    </tr>
                                                )
                                                :
                                                null
                                        }
                                    </table>


                                </div>
                            </div>

                        }
                        <div className="container">
                            <div class="col-md-6 offset-md-3">
                                <Link to="/viewrevisitedproposals">View To Be Revisited Proposals</Link>
                            </div>
                        </div>
                    </div>
                ) : (<h1>You are not Authorized</h1>)
                }
            </div>

        )
    }
}