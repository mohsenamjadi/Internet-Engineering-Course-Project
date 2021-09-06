import React from "react";
import CoursesService from "../../services/CoursesService";

export default class CoursesSearchBar extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            filter: null
        }

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        event.preventDefault();
        this.setState({filter: event.target.value});
        event.preventDefault();
    }

    render() {
        return (
            <div className="search-bar">
                <div className="outer-box">
                    <form action="" className="w-100 m-0 d-flex justify-content-center align-items-center">
                        <div className="row w-100">
                            <div className="food-name col">
                                <input className="search-item" placeholder="نام درس"
                                onChange={this.handleChange}/>
                            </div>
                            <div className="search-btn col">
                                <div className="btn-submit" type="submit" style={{height: '60px', lineHeight: '60px'}}
                                onClick={() => this.props.onSearch(this.state.filter)}>
                                    جستجو
                                    <i className="flaticon-loupe"></i>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}