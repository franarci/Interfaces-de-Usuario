import React from 'react';
import Navbar from './Navbar';
import '../css/player.css';
import axios from 'axios';


class Player extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
            id: this.props.match.params.id
    }
        this.handlePlay = this.handlePlay.bind(this)
}
  
    componentDidMount(){
        console.log("player did mount", this.props.match.params.id)
        console.log("state content id", this.state.id)
    }

    handlePlay(event){      
        axios.post(`http://localhost:7000/user/lastSeen`, {id: this.state.id},
         {headers:{ Authentication: localStorage.getItem('Token')  }})
         .then(console.log("Contenido agregado a lastSeen"))
         .catch ( error =>console.log(error.response.data.message))
        
    
}
    render(){
     return(
        <div className="player">
            <Navbar/>
     <h1>Reproductor</h1>

        <div className="player-container">
            <div className="container">
                <button onClick={this.handlePlay} id="play-middle" className="play-middle">
                    <div id="triangle-right"/>
                </button>
            </div>
            <div id="video-controls" className="controls" data-state="hidden">
                <button id="playpause" type="button" data-state="play">Play/Pause</button>
                <button id="stop" type="button" data-state="stop">Stop</button>
                <button id="mute" type="button" data-state="mute">Mute/Unmute</button>
                <button id="volinc" type="button" data-state="volup">Vol+</button>
                <button id="voldec" type="button" data-state="voldown">Vol-</button>
                <button id="fs" type="button" data-state="go-fullscreen">Fullscreen</button>
            </div>
        </div>

        </div>
        )

        }
    }
export default Player;