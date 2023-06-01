import { useState } from "react";
import { Button } from "../Button";
import { Link, useNavigate } from "react-router-dom";
import "./header.css";
import logo2 from "../../assets/logo2.png";
import BurgerButton from "../BurgerButton/BurgerButton";
import Swal from "sweetalert2";

const Header = () => {
  const [clicked, setClicked] = useState(false);
  const user = JSON.parse(localStorage.getItem("user")) || null
  const navigate = useNavigate();
  const handleClick = () => {
    setClicked(!clicked);
  };

  const handleLogOut = () => {
    Swal.fire({
      title: `Quieres cerrar sesión?`,
      icon: "warning",
      showCancelButton: true,
      showConfirmButton: true,

    }).then((result) => {
      if (result.isConfirmed) {
        localStorage.clear();
        navigate("/")
      }
    });
  };

  return (
    <header>
      <div className="header_logo">
        <Link
          to="/"
          className="link">
          <img
            src={logo2}
            alt="Logo"
          />
        </Link>
        <span>Vive la aventura</span>
      </div>

      {user ? (
        <nav>
          <Button
            type="primary"
            onClick={() => navigate("/user")}>
            {user.name.split('')[0]}
            {user.lastName.split('')[0]}
          </Button>
          <Button
            type="secondary"
            onClick={handleLogOut}>
            Cerrar sesión
          </Button>
        </nav>

      ) : (
        <nav>
          <Button
            type="primary"
            onClick={() => navigate("/login")}>
            Iniciar Sesión
          </Button>
          <Button
            type="secondary"
            onClick={() => navigate("/signup")}>
            Crear Cuenta
          </Button>
        </nav>
      )}

      <div className="burger">
        <BurgerButton
          clicked={clicked}
          handleClick={handleClick}
        />
      </div>
      {user ? (
        <div
          id="bgDiv"
          className={`initial ${clicked ? " active" : ""}`}>
          <div className="bgDiv__content">
            <p onClick={() => navigate("/user")}>{user.name}</p>
            <p onClick={handleLogOut}>Cerrar sesión</p>
          </div>
        </div>
      ) : (
        <div
          id="bgDiv"
          className={`initial ${clicked ? " active" : ""}`}>
          <div className="bgDiv__content">
            <p onClick={() => navigate("/login")}>Crear cuenta</p>
            <p onClick={() => navigate("/signup")}>Iniciar sesión</p>
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;