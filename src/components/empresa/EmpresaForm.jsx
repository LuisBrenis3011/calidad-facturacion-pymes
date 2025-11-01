import { useContext, useEffect, useState } from "react";
import { EmpresaContext } from "../../context/EmpresaContext.jsx";

export const EmpresaForm = ({ empresaSelected, handlerCloseForm }) => {

    const { initialEmpresaForm, handlerAddEmpresa, errors } = useContext(EmpresaContext);
    const [empresaForm, setEmpresaForm] = useState(initialEmpresaForm);
    const { id, razonSocial, ruc, direccion, email } = empresaForm;

    useEffect(() => {
        setEmpresaForm({ ...empresaSelected });
    }, [empresaSelected]);

    const onInputChange = ({ target }) => {
        const { name, value } = target;
        setEmpresaForm({
            ...empresaForm,
            [name]: value,
        });
    };

    const onSubmit = (e) => {
        e.preventDefault();
        handlerAddEmpresa(empresaForm);
    };

    const onClose = () => {
        handlerCloseForm();
        setEmpresaForm(initialEmpresaForm);
    };

    return (
        <form onSubmit={onSubmit}>
            <input
                className="form-control my-2 w-75"
                placeholder="RUC"
                name="ruc"
                value={ruc}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.ruc}</p>

            <input
                className="form-control my-2 w-75"
                placeholder="Razón Social"
                name="razonSocial"
                value={razonSocial}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.razonSocial}</p>

            <input
                className="form-control my-2 w-75"
                placeholder="Dirección"
                name="direccion"
                value={direccion}
                onChange={onInputChange}
            />

            <input
                className="form-control my-2 w-75"
                placeholder="Email"
                name="email"
                value={email}
                onChange={onInputChange}
            />
            <p className="text-danger">{errors?.email}</p>

            <input type="hidden" name="id" value={id} />

            <button className="btn btn-primary" type="submit">
                {id > 0 ? 'Editar' : 'Crear'}
            </button>

            {!handlerCloseForm || (
                <button
                    className="btn btn-secondary mx-2"
                    type="button"
                    onClick={onClose}>
                    Cerrar
                </button>
            )}
        </form>
    );
};
