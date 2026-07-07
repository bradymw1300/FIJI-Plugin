# LabQuant-Fiji

LabQuant-Fiji is a Fiji/ImageJ plugin scaffold for modular lab image quantification workflows.

This first-step scaffold provides:

- A SciJava command at `Plugins > LabQuant > Run Batch Quantification`
- A batch workflow skeleton with per-image failure handling
- Deterministic recursive image discovery
- Basic `ImagePlus` loading through `IJ.openImage()`
- A dummy assay that records image dimensions and calibration metadata
- CSV result and failure reports
- Provenance files for workflow configuration and run manifest

No biological assay logic is implemented yet.

## Build

```bash
mvn package
```

The project targets Java 8 bytecode for Fiji compatibility.

## Current Dummy Outputs

Running the command writes a `LabQuant_Output` folder under the selected output folder:

```text
LabQuant_Output/
├─ results/per_image_results.csv
├─ results/failed_images.csv
├─ qc/
├─ logs/
├─ logs/stacktraces/
├─ provenance/workflow_config.json
├─ provenance/run_manifest.json
└─ temp/
```

The dummy assay opens each discovered image and records:

```text
source_path,image_title,width_px,height_px,channels,slices,frames,bit_depth,pixel_width_um,pixel_height_um
```
