import React from 'react'
import SupplierNavbar from '../component/SupplierNavbar';

const supplierDataInfo = JSON.parse(localStorage.getItem('info'))

export default class ListProposalReport extends React.Component {


    constructor() {
        super();
        this.state = {
            proposals: null,
            pstatus: false,
            emptyData: false

        }

    }

    componentDidMount() {
        const supplierData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/proposal/viewbysupplier/${supplierData.id}`, {
            headers: {
                'Authorization': `Bearer ${supplierData.token}`
            }
        }).then((res) => {

            if (res.status === 200) {
                return res.json();
            }
            else if (res.status === 400) {
                this.setState({ emptyData: true },)
                throw new Error("No Data Found")
            } else if (res.status === 401) {
                throw new Error("Unauthorized Access. Please login again")
            } else {
                throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
            }

        }).then((res) => {
            this.setState({ proposals: res })
        }).catch((e) => console.log(e))

    }
    render() {
        return (
            <div>
                {supplierDataInfo.role === "supplier" ? (
                    <div>
                        <SupplierNavbar />
                        {this.state.emptyData === true ? (

                            <div className="container">
                                <div class="col-md-6 offset-md-3">
                                    <br />
                                    <br />
                                    <br />
                                    <br />
                                    <h3><strong>No Proposals Submitted</strong></h3>
                                </div>
                            </div>
                        ) :
                            <div className="container">
                                <div >
                                    <br /><br />
                                    <h3>Your Proposals</h3><br />
                                    <table class="table table-bordered ">
                                        <tr>
                                            <th>ID</th>
                                            <th>Requirement ID</th>
                                            <th>Delivery Date</th>
                                            <th>Quotation</th>
                                            <th>Status</th>

                                        </tr>
                                        {
                                            this.state.proposals ?
                                                this.state.proposals && this.state.proposals.map((proposal, i) =>
                                                    <tr>
                                                        <td> {proposal.id}</td>
                                                        <td> {proposal.requirementId} </td>
                                                        <td> {proposal.proposalDate}</td>
                                                        <td> {proposal.quotation}</td>
                                                        <td>{proposal.status}</td>
                                                    </tr>
                                                )
                                                :
                                                null}

                                    </table>
                                </div>
                            </div>
                        }
                    </div>
                ) : (<h1>You are not Authorized</h1>)
                }

            </div>
        )
    }
} 