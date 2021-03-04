import React from 'react';
import Nav from 'react-bootstrap/Nav';
import { Link } from 'react-router-dom';
import SupplierNavbar from './SupplierNavbar';

const supplierData = JSON.parse(localStorage.getItem('info'))

export class SetAmenities extends React.Component {

    constructor(props) {
        super(props);
        this.state = { storageVariable: '', contracts: [], isLoading: true, emptyData: false }
    }

    handleOnClick = (event) => {
        this.setState({ storageVariable: event.target.value }, console.log(this.state.storageVariable))
        this.setState({ amenityFlag: true }, console.log("AmenityFlag: " + this.state.amenityFlag))
    }

    componentDidMount() {
        let status = 'Waiting For Approval'
        let url = 'http://localhost:8080/contract/both/contracts/' + JSON.parse(localStorage.getItem('info')).id + '/' + status
        fetch(url, {

            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${JSON.parse(localStorage.getItem('info')).token}`
            }

        }).then((response) => {
            if (response.status === 200) {
                return response.json()
            }
            else {
                throw new Error("Error")
            }
        }).then((json) => {
            if (json.hasOwnProperty("message")) {
                this.setState({ emptyData: true })
                this.setState({ isLoading: true }, () => console.log(this.state.isLoading))
            }
            else { this.setState({ contracts: json }) }
        }).catch((error) => console.error(error))
            .finally(() => this.setState({ isLoading: false }))
    }
    handleSubmit = (event) => {
        event.preventDefault();
        let url = 'http://localhost:8080/contract/supplier/createAndModify'
        let requestBody = { "id": this.state.storageVariable, "amenities": this.state.amenityText }
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
                    this.setState({ amenityFlag: false })
                    this.setState({ amenityText: '' }, console.log(this.state.amenityText))
                    return 'Amenities are added to the contract Successfully'
                }
                else if (response.status === 400) {
                    throw new Error("Supplier With the given Id is Not Found")
                }
                else {
                    throw new Error("Unknown Error Occured. Could Not Create Contract. Please Try Again")
                }
            }

            ).then((text) => {
                alert(text)
                this.props.history.push('/amenities')
            })
                .catch((e) => alert("Could Not Create Contract. Please Try again"))
        } catch (error) {
            console.log(error);
        }


    }

    handleOnChange = (event) => this.setState({ [event.target.name]: event.target.value })

    render() {
        return (
            <div>
                {supplierData.role === "supplier" ? (
                    <div>
                        <SupplierNavbar />
                        {this.state.emptyData === true ? <h1>No Data Available</h1> :
                            <div className="container col-4">
                                <h1>Set Amenities</h1>
                                {this.state.isLoading ? <li>Data is Loading</li> :
                                    <table className="table table-bordered text-center">
                                        <thead>
                                            <tr>
                                                <th scope="col">ContractId</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.state.contracts.map((contract) =>
                                                <tr key={contract}>
                                                    <td> <Nav.Link>
                                                        <Link className="text-muted" to={{ pathname: "/enteramenities/" + contract }}>{contract}</Link>
                                                    </Nav.Link></td>
                                                </tr>)}
                                        </tbody>
                                    </table>}

                            </div>}
                    </div>
                ) : (<h1>You are not Authorized</h1>)}

            </div>
        )
    }
}