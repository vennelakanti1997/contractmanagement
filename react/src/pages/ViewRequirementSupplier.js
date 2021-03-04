import React from 'react'
import SupplierNavbar from '../component/SupplierNavbar';
import { Link } from 'react-router-dom'


const supplierDataInfo = JSON.parse(localStorage.getItem('info'))

export default class ViewRequirementSupplier extends React.Component {


    constructor() {
        super();
        this.state = {
            requirementids: null,
            emptyData: false
        }

    }




    componentDidMount() {
        const supplierData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/requirement/viewbysupplier/${supplierData.id}`, {
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

            this.setState({ requirementids: res })
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
                                    <h3><strong>No Requirements Matching your Type</strong></h3>
                                    <br />
                                    <br />

                                    <Link variant="primary" to="/viewsupplierproposals" >View Proposals</Link>
                                </div>
                            </div>
                        ) :
                            <div class="row">
                                <div class="col-md-6 offset-md-3">
                                    <br /><br />
                                    <h3> Requirements matching your Type</h3><br />
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Serial no.</th>
                                            <th>Requirement ID</th>
                                            <th></th>
                                        </tr>
                                        {
                                            this.state.requirementids ?
                                                this.state.requirementids && this.state.requirementids.map((requirementid, i) =>
                                                    <tr>
                                                        <td>{i + 1}</td>
                                                        <td> {requirementid}</td>
                                                        <td><Link to={{
                                                            pathname: "/viewreqs/" + requirementid, state: {
                                                                requirementid: this.props.rid
                                                            }
                                                        }} >View Details</Link></td>
                                                    </tr>
                                                )
                                                :
                                                null
                                        }
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






