import React from 'react';
import './style.css';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

const Modal: React.FC<ModalProps> = ({ isOpen, onClose, onConfirm }) => {
  if (!isOpen) return null;

  return (
    <div className="modalOverlay">
      <div className="modalContainer">
        <div className="modalTitle">정말로 주문을 취소하시겠습니까?</div>
        <div className="modalButtons">
          <button className="confirmButton" onClick={onConfirm}>확인</button>
          <button className="cancelButton" onClick={onClose}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default Modal;