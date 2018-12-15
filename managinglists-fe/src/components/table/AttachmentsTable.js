import React from "react";

const AttachmentsTable = props => {
  // Data
  const dataColumns = props.dataColumns;
  const dataRows = props.dataRows;
  const keyField = props.keyField;

  const tableHeaders = (
    <thead>
      <tr>
        {dataColumns.map(function(column, index) {
          return <th key={index}>{column.text}</th>;
        })}
        <th />
      </tr>
    </thead>
  );

  const tableBody = dataRows.map(function(row) {
    return (
      <tr key={row[keyField]}>
        {dataColumns.map(function(column, index) {
          return <td key={index}>{row[column.dataField]}</td>;
        })}
        <td align="center" onClick={() => props.handleClick(row)}>
          <span className="fa fa-trash" />
        </td>
      </tr>
    );
  });

  // Decorate with Bootstrap CSS
  return (
    <div className="table-responsible">
      <table className="table table-bordered" width="100%">
        {tableHeaders}
        <tbody>{tableBody}</tbody>
      </table>
    </div>
  );
};

export default AttachmentsTable;
