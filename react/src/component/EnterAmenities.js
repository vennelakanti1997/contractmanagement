import React from 'react';
import Button from 'react-bootstrap/Button';
import SupplierNavbar from './SupplierNavbar';

const supplierData = JSON.parse(localStorage.getItem('info'))


export class EnterAmenities extends React.Component {

    constructor(props) {
        super(props);
        this.state = { amenityText: '' }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        let url = 'http://localhost:8080/contract/supplier/createAndModify'
        let requestBody = { "id": this.props.match.params.cid, "amenities": this.state.amenityText }
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
                this.props.history.push("/amenities")
            })
                .catch((e) => alert(e))
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
                        <form onSubmit={this.handleSubmit} className="container mx-auto pt-5">
                            <div className="form-group text-left col-4">

                                <h1 className="Col">Set Amenities</h1>
                                <h6>Contract Id: {this.props.match.params.cid}</h6>
                            </div>

                            <div className="form-group text-left col-4">
                                <label htmlFor="amenityText">Set Amenities</label>
                                <textarea maxLength="500" name="amenityText" className="form-control" value={this.state.amenityText} onChange={this.handleOnChange} />
                            </div>

                            <div className="form-group text-left col-4">
                                <Button variant="primary" type="submit" className="mr-5">Submit</Button>
                            </div>

                        </form>
                    </div>
                ) : (<h1>You are not Authorized</h1>)}

            </div>
        )
    }
}