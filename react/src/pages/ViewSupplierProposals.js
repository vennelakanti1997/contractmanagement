import React from 'react'
import SupplierNavbar from '../component/SupplierNavbar';
import { Link } from 'react-router-dom'


const supplierDataInfo = JSON.parse(localStorage.getItem('info'))


export default class ViewRequirementSupplier extends React.Component {


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
                                    <br />
                                    <br />

                                    <Link to={"/viewreqsupplier"} >View Requirements</Link>
                                </div>
                            </div>
                        ) :
                            <div class="row">
                                <div class="col-md-6 offset-md-3">
                                    <br /><br />
                                    <h3>Your Proposals</h3><br />
                                    <table class="table table-bordered ">
                                        <tr>
                                            <th>ID</th>
                                            <th>Requirement ID</th>
                                            <th>Delivery Date</th>
                                            <th>Quotation</th>
                                            <th>Status</th>
                                            <th></th>
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
                                                        {proposal.status === "To Be Revisited" ? (<td><Link to={{
                                                            pathname: "/editproposal/" + proposal.id, state: {
                                                                proposalid: this.props.pid
                                                            }
                                                        }} >Edit Proposal</Link></td>) : <td> </td>}

                                                    </tr>
                                                )
                                                :
                                                null}

                                    </table>

                                </div>
                            </div>
                        }
                    </div>
                ) : (<h1>You are not Authorized</h1>)}

            </div>
        )
    }
} 