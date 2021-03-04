import React from 'react'
import axios from 'axios';
import AdminNavbar from '../component/AdminNavbar';




export let proposalId = ""
export let proposalStatus = ""

const adminData = JSON.parse(localStorage.getItem('info'))

export default class ViewRequirements extends React.Component {


    constructor() {
        super();
        this.state = {
            proposal: null,
            status: "",
            statusError: ""
        }

    }



    componentDidMount() {
        const adminData = JSON.parse(localStorage.getItem('info'))
        fetch(`http://localhost:8080/proposal/viewbyid/` + this.props.match.params.pid, {
            headers: {
                'Authorization': `Bearer ${adminData.token}`
            }
        }).then((res) => {

            res.json().then((result) => {

                console.warn(result);

                this.setState({ proposal: result })

            })

        })

    }

    handleSubmit1 = (event) => {
        event.preventDefault();
        const isValid = this.validate();
        if (isValid) {
            const pstatus = this.state.status;
            const adminData = JSON.parse(localStorage.getItem('info'))
            axios.put(`http://localhost:8080/proposal/editstatus/${this.state.proposal.id}?status=${pstatus}`,
                {

                },
                {
                    headers: {
                        'Authorization': `Bearer ${adminData.token}`
                    }
                })

                .then(res => {
                    console.log(res.data);
                    proposalId = this.state.proposal.id;
                    proposalStatus = this.state.status;
                    this.props.history.push('/statusedited')
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
        let statusError = "";
        if (this.state.status.length === 0) {
            statusError = "Select Status for Proposal"
            this.setState({ statusError })
            return false
        } else {
            this.setState({
                typeError: "",
            })
        }
        return true

    }
    render() {
        return (
            <div>
                {adminData.role === "admin" ? (
                    <div>
                        <AdminNavbar />
                        <div class="row">
                            <div class="col-md-8 offset-md-4">
                                <br /><br />
                                <h2>Proposal Details</h2><br />
                                <table class="table table-bordered">

                                    {
                                        this.state.proposal ?
                                            <div> <tr>
                                                <td>Proposal Id: {this.state.proposal.id}</td>
                                            </tr>
                                                <tr>
                                                    <td>Proposal Quotation: {this.state.proposal.quotation}</td>
                                                </tr>
                                                <tr>
                                                    <td>Proposal Date: {this.state.proposal.proposalDate}</td>
                                                </tr>
                                                <tr>
                                                    <td>Proposal Current Status: {this.state.proposal.status}</td>
                                                </tr>
                                            </div>
                                            :
                                            null
                                    }
                                </table>


                                <form onSubmit={this.handleSubmit1}>
                                    <div className="form-inner">

                                        <div className="form-group">
                                            <div>
                                                <label style={{ "padding": "10px 10px 10px 0" }}>Proposal Status</label>
                                                <select
                                                    name="status"
                                                    value={this.state.status}
                                                    onChange={this.handleChange} >
                                                    <option selected>Select an option</option>
                                                    <option value="Approved">Approved</option>
                                                    <option value="Rejected">Rejected</option>
                                                    <option value="To Be Revisited">To Be Revisited</option>
                                                </select>
                                                <div className="error">{this.state.statusError}</div>
                                            </div>
                                        </div>

                                        <button type="submit" className="btn btn-primary" >Change Status</button>
                                    </div>
                                </form>
                                <div>
                                </div>
                            </div>
                        </div>
                    </div>
                ) : (<h1>You are not Authorized</h1>)}

            </div>
        )
    }
}  