{
	description = "Bachelorprojekt";

	inputs = {
		nixpkgs.url = "nixpkgs/nixos-unstable";
		flake-utils.url = "github:numtide/flake-utils/v1.0.0";
	};

	outputs = {flake-utils, nixpkgs, ...}:
		flake-utils.lib.eachDefaultSystem (system:
			let pkgs = nixpkgs.legacyPackages."${system}";
			in {
				devShells.default = pkgs.mkShell {
					name = "Bachelorprojekt shell";
					packages = with pkgs; [
						gnumake
						jdk
						maven
					];
				};
			}
		);
}
