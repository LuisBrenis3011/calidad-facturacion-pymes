import { useContext } from "react";
import { EmpresaContext } from "../../context/EmpresaContext.jsx";
import { EmpresaForm } from "./EmpresaForm.jsx";

export const EmpresaModalForm = () => {

    const { empresaSelected, handlerCloseForm } = useContext(EmpresaContext);

    return (
        <div className="abrir-modal animacion fadeIn">
            <div className="modal" style={{ display: "block" }} tabIndex="-1">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">
                                {empresaSelected.id > 0 ? 'Editar Empresa' : 'Nueva Empresa'}
                            </h5>
                        </div>
                        <div className="modal-body">
                            <EmpresaForm
                                empresaSelected={empresaSelected}
                                handlerCloseForm={handlerCloseForm}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
