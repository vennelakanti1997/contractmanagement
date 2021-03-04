import React, { Component } from 'react'
import axios from 'axios';
import SupplierNavbar from '../component/SupplierNavbar';

const initialState = {
    quotation: "",
    proposalDate: "",
    quotationError: "",
    proposalDateError: "",
}

export let proposalRequirementId = ""
export let proposalQuotation = ""
export let proposalDate = ""

const supplierData = JSON.parse(localStorage.getItem('info'))

export default class CreateProposal extends Component {


    state = initialState

    handleSubmit = (event) => {
        event.preventDefault();
        const isValid = this.validate();
        if (isValid) {
            axios.post(`http://localhost:8080/proposal/create/${this.props.match.params.rid}/${supplierData.id}`,
                {
                    quotation: this.state.quotation,
                    proposalDate: this.state.proposalDate,
                }, {
                headers: {
                    'Authorization': `Bearer ${supplierData.token}`
                }
            })

                .then(res => {
                    console.log(res.data);
                    proposalQuotation = this.state.quotation;
                    proposalDate = this.state.proposalDate;
                    proposalRequirementId = this.props.match.params.rid;
                    this.props.history.push('/proposaldetails')
                })
                .catch(err => {
                    console.log(err.response)
                })
        }
    }

    handleChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        })
        console.log(event.target.name);
    }

    validate = () => {


        let proposalDateError = "";
        let quotationError = "";
        const todayDate = new Date().toISOString().slice(0, 10);

        if (Date.parse(this.state.proposalDate) < Date.parse(todayDate)) {
            proposalDateError = "Select Appropriate Delivery Date"
            this.setState({ proposalDateError })
            return false

        }

        if (this.state.quotation.length === 0) {
            quotationError = "Enter Quotation"
            this.setState({ quotationError })
            return false
        }

        return true

    }

    render() {
        return (
            <div>
                {supplierData.role === "supplier" ? (
                    <div>
                        <SupplierNavbar />

                        <div className="container">

                            <form onSubmit={this.handleSubmit}>
                                <div className="form-inner">
                                    <h2>Create Proposal</h2>


                                    <div className="form-group">
                                        <label htmlFor="proposalDate">Proposed Delivery Date</label>
                                        <input
                                            type="date"
                                            id="proposalDate"
                                            className="form-control"
                                            name="proposalDate"
                                            placeholder="enter proposal date(xx/xx/xxxx)"
                                            value={this.state.proposalDate}
                                            onChange={this.handleChange} />
                                        <div className="error">{this.state.proposalDateError}</div>
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="quotation">Quotation</label>
                                        <input
                                            type="number"
                                            id="quotation"
                                            className="form-control"
                                            name="quotation"
                                            placeholder="enter Quotation"
                                            value={this.state.quotation}
                                            onChange={this.handleChange} />
                                        <div className="error">{this.state.quotationError}</div>
                                    </div>


                                    <button type="submit"
                                        className="btn btn-primary" >Create</button>


                                </div>

                            </form>
                        </div>
                    </div>
                ) : (<h1>You are not Authorized</h1>)}


            </div>
        )
    }

}
