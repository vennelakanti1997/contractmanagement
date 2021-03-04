import React from 'react';
import SupplierNavbar from './SupplierNavbar';
import AdminNavbar from './AdminNavbar';
import Nav from 'react-bootstrap/Nav';
import { Link } from 'react-router-dom';

export class ManageDelivery extends React.Component {
    constructor(props) {
        super(props);
        this.state = { data: [], isLoading: false, emptyData: false }
    }



    handleOnClick = (id) => {
        let url = 'http://localhost:8080/contract/supplier/createAndModify'
        let requestBody = {

            "id": id,
            "status": "Active - Delivered"
        }
        try {
            fetch(url, {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`
                },
                body: JSON.stringify(requestBody)
            }).then((response) => {
                if (response.status === 200) {

                    return "Status updated successfully"
                }
                else if (response.status === 400) {
                    throw new Error("Contract with the given Id not found")
                }
                else if (response.status === 401) {
                    throw new Error("UnAuthorized Access. Please login again")
                }
                else {
                    throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
                }
            }

            ).then((text) => {
                this.props.history.push('/managedelivery')
                window.location.reload()
                alert(text)

            })
                .catch((e) => alert(e))
        } catch (error) {
            console.log(error);
        }
    }
    componentDidMount() {
        var url;
        if (JSON.parse(localStorage.getItem('info')).role === "supplier") {
            url = 'http://localhost:8080/contract/both/active/' + JSON.parse(localStorage.getItem('info')).id
        }
        else {
            url = url = 'http://localhost:8080/contract/contractor/activeids'
        }
        try {
            fetch(url, {
                headers: {
                    'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`

                },

            }).then(response => {
                if (response.status === 200) {

                    return response.json()
                }
                else if (response.status === 401) {
                    throw new Error("UnAuthorized Access. Please login again")
                }
                else if (response.status === 400) {
                    throw new Error("Supplier With the given Id is Not Found")
                }
                else {
                    throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
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
            })
                .catch((e) => alert("Error: " + e.toString()))
        }
        catch (e) {
            alert("In catch block")
            console.log(e.message)
        }
    }


    render() {
        if (this.state.isLoading === false) {
            return <div><SupplierNavbar /> <h6>Data is Loading...</h6></div>
        }
        else {
            if (this.state.emptyData === true) {
                return <div>
                    {JSON.parse(localStorage.getItem('info')).role === "supplier" ? <SupplierNavbar /> : <AdminNavbar />}
                    <h1>No Data available</h1>
                </div>
            }
            else {
                return (
                    <div>
                        {JSON.parse(localStorage.getItem('info')).role === "supplier" ? <SupplierNavbar /> : <AdminNavbar />}
                        {JSON.parse(localStorage.getItem('info')).role === "supplier" ? <h6 className="text-center">Click on the contract id to update the status to 'Delivered' Once the status is updated, it cannot be modified.</h6> : null}
                        <table className="table container text-center" style={{ "width": "60%" }}>
                            <thead>
                                <tr>
                                    <th>Contract Id</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {JSON.parse(localStorage.getItem('info')).role === "supplier" ? this.state.data.map((contract) =>
                                    <tr>
                                        <td>{contract[1] === 'Active - Delivered' ? contract[0] :
                                            // <Button variant="link" onClick={() => this.handleOnClick(contract[0])}>{contract[0]}</Button>
                                            <Nav.Link>
                                                <Link className="text-muted" onClick={() => this.handleOnClick(contract[0])}>{contract[0]}
                                                </Link>
                                            </Nav.Link>
                                        }</td>
                                        <td>{contract[1]}</td>
                                    </tr>
                                ) : this.state.data.map((contract) =>
                                    <tr>
                                        <td>{contract[0]}</td>
                                        <td>{contract[1] === 'Active - Delivered' ? "Delivered" : "Not Yet Delivered"}</td>
                                    </tr>
                                )

                                }
                            </tbody>
                        </table>

                    </div>)
            }
        }
    }
}