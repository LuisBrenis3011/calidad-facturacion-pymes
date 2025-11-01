import { useContext } from "react";
import { ProductoForm } from "./ProductoForm.jsx";
import {ProductoContext} from "../../context/ProductoContext.jsx";

export const ProductoModalForm = ({empresaId}) => {

    const { productoSelected, handlerCloseForm } = useContext(ProductoContext);

    return (
        <div className="abrir-modal animacion fadeIn">
            <div className="modal" style={{ display: "block" }} tabIndex="-1">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">
                                {productoSelected.id > 0 ? 'Editar' : 'Crear'} Producto
                            </h5>
                        </div>
                        <div className="modal-body">
                            <ProductoForm
                                productoSelected={productoSelected}
                                handlerCloseForm={handlerCloseForm}
                                empresaId={empresaId}
                            />

                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};
