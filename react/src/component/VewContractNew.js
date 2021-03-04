import React from 'react';
import Button from 'react-bootstrap/Button';
import Nav from 'react-bootstrap/Nav';
import { Link } from 'react-router-dom';
import AdminNavbar from './AdminNavbar';
import SupplierNavbar from './SupplierNavbar';
import { ActionButton } from './ActionButton';

const DisplayRows = (props) => {
    if (props.flag === "supplier" && props.status === "Closed") {
        console.log(props.flag + props.status)
        return null;
    }
    else {
        return <tr>
            {props.flag === "supplier" ? <td>{props.status === "Waiting For Approval" ?
                <Nav.Link>
                    <Link className="text-muted" to={{
                        pathname: "/edit/" + props.cid,
                        state: {
                            userId: props.cid
                        }
                    }}>{props.cid}
                    </Link>
                </Nav.Link> : props.cid}</td> : <td>{props.cid}</td>}
            {props.flag === "supplier" ? null : <td>{props.sid}</td>}
            <td>{props.type}</td>
            <td>{props.duration}</td>
            <td >{props.status === "Active - Delivered" || props.status === "Active - Not Delivered" ?
                "Active" : props.status}
            </td>
            <td>{props.tnc}</td>
            <td>{props.amenities}</td>
            {props.flag === "supplier" ? null :
                <td>{props.status === "Waiting For Approval" &&
                    <ActionButton
                        cid={props.cid}
                        flag="AcceptReject" />
                }
                    {props.status.includes("Active") &&
                        <ActionButton
                            cid={props.cid}
                            flag="CloseExtend" />
                    }
                </td>
            }
        </tr>
    }
}
export class ViewContractNew extends React.Component {
    constructor(props) {
        super(props);
        this.state = { isLoading: false, data: [], createFlag: false, feedbackFlag: false, emptyData: false }
    }
    componentDidMount() {
        var url = ''
        if (JSON.parse(localStorage.getItem('info')).role === "supplier") {
            url = 'http://localhost:8080/contract/supplier/contracts/' + JSON.parse(localStorage.getItem('info')).id
        }
        else {
            url = 'http://localhost:8080/contract/contractor/contracts'
        }

        const supplierData = JSON.parse(localStorage.getItem('info'))
        try {
            fetch(url, {
                headers: {
                    'Authorization': `Bearer ${supplierData.token}`

                },

            }).then(response => {
                if (response.status === 200) {
                    return response.json()
                }

            }).then((json) => {
                if (json.hasOwnProperty("message")) {
                    this.setState({ emptyData: true })
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
                else {
                    this.setState({ data: json });
                    this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
                }
            }
            )
                .catch((e) => alert("Error: " + e.toString()))
        }
        catch (e) {
            alert("In catch block")
            console.log(e.message)
        }

    }
    render() {
        if (this.state.isLoading === false) {

            return <div>
                {JSON.parse(localStorage.getItem('info')).role === "supplier" ? <SupplierNavbar /> : <AdminNavbar />}
                <div>Data is loading...</div>
            </div>
        }
        else {


            return (

                <div>
                    {JSON.parse(localStorage.getItem('info')).role === "supplier" ? <SupplierNavbar /> : <AdminNavbar />}
                    {this.state.emptyData === true ? (
                        <div className="container">
                            <h1>No Data Available</h1>
                            <Button variant="primary" href="/createcontract" >Create first Contract</Button>
                        </div>
                    ) :

                        <table className="table container-fluid text-center" style={{ "width": "80%" }} >
                            <thead>
                                {JSON.parse(localStorage.getItem('info')).role === "supplier" ?
                                    <tr>
                                        {/* <td></td> */}
                                        <td colSpan="3" className="text-left">
                                            <Button
                                                variant="primary"
                                                href="/createcontract"
                                                type="submit"
                                                className="mr-5"
                                                onClick={() => this.setState({ createFlag: true },
                                                    () => console.log(this.state.createFlag))}>Create Contract
                                               </Button>
                                        </td>
                                        <td></td>
                                        {/* <td></td> */}
                                        <td colSpan="2" className="text-right">
                                            {/* <Button
                                                variant="primary"
                                                href="/createcontract"
                                                type="submit"
                                                className="mr-5"
                                                onClick={() => this.setState({ feedbackFlag: true },
                                                    () => console.log(this.state.feedbackFlag))}>Feedback
                                               </Button> */}
                                        </td>
                                    </tr> : <tr>
                                        <td className="text-right" colSpan="6">
                                            {/* <Button
                                            variant="primary"
                                            type="submit"
                                            className="mr-5">View Feedback
                                          </Button> */}
                                        </td>
                                    </tr>
                                }
                                <tr>
                                    <th scope="col">Contract Id</th>
                                    {JSON.parse(localStorage.getItem('info')).role !== "supplier" ? <th scope="col">Supplier Id</th> : null}
                                    <th scope="col">Contract Type</th>
                                    <th scope="col">Duration</th>
                                    <th scope="col">Status</th>
                                    <th scope="col">Terms And Conditions</th>
                                    <th scope="col">Amenities</th>
                                    {JSON.parse(localStorage.getItem('info')).role === "supplier" ?
                                        null :
                                        <th
                                            scope="col"
                                        >Action</th>
                                    }
                                </tr>

                            </thead>
                            <tbody>


                                {this.state.data.map((contract) =>
                                    <DisplayRows
                                        key={contract.id}
                                        cid={contract.id}
                                        sid={contract.supplierId}
                                        type={contract.contractType}
                                        duration={contract.duration}
                                        tnc={contract.tnc}
                                        amenities={contract.amenities}
                                        status={contract.status}
                                        flag={JSON.parse(localStorage.getItem('info')).role}
                                    />)}


                            </tbody>
                        </table>}
                </div>
            )
        }


    }


}
