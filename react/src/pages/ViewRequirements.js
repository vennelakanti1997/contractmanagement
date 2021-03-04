import React from 'react'
import SupplierNavbar from '../component/SupplierNavbar';
import { Link } from 'react-router-dom'

const supplierData = JSON.parse(localStorage.getItem('info'))

export default class ViewRequirements extends React.Component {


    constructor() {
        super();
        this.state = {
            requirement: null
        }

    }



    componentDidMount() {
        const supplierData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/requirement/viewbyid/` + this.props.match.params.rid, {
            headers: {
                'Authorization': `Bearer ${supplierData.token}`
            }
        }).then((res) => {

            res.json().then((result) => {

                console.warn(result);

                this.setState({ requirement: result })

            })

        })

    }
    render() {
        return (
            <div>
                {supplierData.role === "supplier" ? (
                    <div>
                        <SupplierNavbar />
                        <div className="container">
                            <div className="form-inner">
                                <br /><br />
                                <h3>Requirement Details</h3><br />
                                <table class="table table-bordered">

                                    {
                                        this.state.requirement ?
                                            <div> <tr>
                                                <td>Requirement Id:  <strong>{this.state.requirement.id}</strong></td>
                                            </tr>
                                                <tr>
                                                    <td>Requirement Description: <strong>{this.state.requirement.description}</strong></td>
                                                </tr>
                                                <tr>
                                                    <td>Requirement Expected Delivery Date: <strong>{this.state.requirement.deliveryDate}</strong></td>
                                                </tr>
                                                <br />
                                                <br />
                                                <Link to={{
                                                    pathname: "/createproposal/" + this.state.requirement.id, state: {
                                                        requirementid: this.props.rid
                                                    }
                                                }}>Create Proposal</Link>

                                            </div>
                                            :
                                            null
                                    }
                                </table>


                            </div>
                        </div>
                    </div>
                ) : (
                        <h1>You are not Authorized</h1>
                    )
                }

            </div >
        )
    }
}
