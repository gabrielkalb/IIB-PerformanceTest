<?php

// Body of the script
//
// Create a script including a class and evaluate method:
// The Invoke evaluate method property of the PHPCompute node is selected by default, 
// therefore a class and evaluate method are expected in the PHP script.
//
// The PHP code must contain a class with the same name as the PHP file (ReadCSVFileFull_PHP_PHP_Compute, for example, it is case insensitive), 
// and this class must contain a function called evaluate, with parameters for the input and output message assemblies:
//
class ReadCSVFileFull_PHP_PHP_Compute {

		/**
		 * An example of MessageBrokerSimpleTransform
		 * @MessageBrokerSimpleTransform
		 */
		function evaluate($output_assembly, $input_assembly)	{
			//InputRoot.DFDL.Teste.record[]
			$output_assembly->Properties = $input_assembly->Properties;
			$records = $input_assembly->DFDL->Teste->record;
			foreach($records as $record) {
				$output_assembly->XMLNSC->records->record[] = $record;
			}
		}
}

?>
